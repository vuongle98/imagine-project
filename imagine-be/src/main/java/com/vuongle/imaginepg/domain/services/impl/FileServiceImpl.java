package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.FileUploadCommand;
import com.vuongle.imaginepg.application.dto.FileDto;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.exceptions.StorageException;
import com.vuongle.imaginepg.application.queries.FileFilter;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.repositories.FileRepository;
import com.vuongle.imaginepg.domain.services.FileService;
import com.vuongle.imaginepg.infrastructure.specification.FileSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.StorageUtils;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import jakarta.activation.FileTypeMap;
import jakarta.activation.MimetypesFileTypeMap;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final Path rootLocation;
    @Value("${imagine.root.file.path}")
    private String ROOT_UPLOAD_PATH;

    public FileServiceImpl(
            FileRepository fileRepository
    ) {
        this.fileRepository = fileRepository;
        this.rootLocation = Paths.get("data");
    }

    @Override
    public FileDto getById(UUID id) {
        return getById(id, FileDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        File file = fileRepository.getById(id);

        // check permission
//        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(file, File.class)) {
//            throw new NoPermissionException("No permission");
//        }

        return ObjectData.mapTo(file, classType);
    }

    @Override
    public FileDto create(FileUploadCommand command) {
        return null;
    }

    @Override
    public FileDto update(UUID id, FileUploadCommand command) {
        return null;
    }

    @Override
    public void delete(UUID id, boolean force) {

        File file = getById(id, File.class);
        if (force) {

            // delete from storage
            StorageUtils.deleteFile(file.getFilePath());

            // delete from db
            fileRepository.deleteById(id);
            return;
        }

        file.setDeletedAt(Instant.now());
        fileRepository.save(file);
    }

    @Override
    public Page<FileDto> getPageable(FileFilter filter, Pageable pageable) {
        Specification<File> specification = FileSpecifications.withFilter(filter);
        Page<File> filePage = fileRepository.findAll(specification, pageable);
        return filePage.map(file -> ObjectData.mapTo(file, FileDto.class));
    }

    @Override
    public List<FileDto> getList(FileFilter filter) {
        Specification<File> specification = FileSpecifications.withFilter(filter);
        return ObjectData.mapListTo(fileRepository.findAll(specification), FileDto.class);
    }

    @Override
    public FileDto uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename()) || file.getOriginalFilename().isEmpty()) {
            throw new StorageException("File or fileName is empty");
        }

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        Path path = this.rootLocation.resolve(Paths.get(StorageUtils.buildDateFilePath(), file.getOriginalFilename())).normalize();

        if (!path.toString().startsWith(this.rootLocation.toString())) {
            throw new StorageException("Cannot save file outside root directory");
        }

        // copy file to storage
        return saveFileInfo(file.getInputStream(), path, fileName, contentType);
    }

    @Override
    public Boolean uploadFileChunk(FileUploadCommand command) throws IOException {

        if (command.getFile().isEmpty()) {
            throw new StorageException("File is empty");
        }

        String fileName = command.getIdentifier() +
                ".part" +
                command.getChunk();
        Path path = this.rootLocation.resolve(Paths.get(
                StorageUtils.buildDateFilePath(),
                "chunks",
                fileName)).normalize();

        if (!path.toString().startsWith(this.rootLocation.toString())) {
            throw new StorageException("Cannot save file outside root directory");
        }

        // copy file to storage
        try (InputStream inputStream = command.getFile().getInputStream()) {
            StorageUtils.createFile(inputStream, path);

//        OutputStream outputStream = new FileOutputStream(path.toString());
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//          outputStream.write(buffer, 0, bytesRead);
//        }

            return true;
        }
    }

    @Override
    public FileDto mergeFileChunk(FileUploadCommand command) throws IOException {

        Path path = this.rootLocation.resolve(Paths.get(
                StorageUtils.buildDateFilePath(),
                command.getFileName())
        ).normalize();

        if (!path.toString().startsWith(this.rootLocation.toString())) {
            throw new StorageException("Cannot save file outside root directory");
        }

        try (OutputStream out = new FileOutputStream(path.toString())) {
            for (int i = 1; i <= command.getTotalChunks(); i++) {

                String chunkName = command.getIdentifier() +
                        ".part" + i;

                Path chunkPath = rootLocation.resolve(Paths.get(
                        StorageUtils.buildDateFilePath(),
                        "chunks",
                        chunkName)
                ).normalize();

                java.io.File chunkFile = new java.io.File(chunkPath.toString());

                if (!chunkFile.exists()) {
                    throw new StorageException("File does not exist");
                }

                try (InputStream in = new FileInputStream(chunkFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                chunkFile.delete();
            }

            File fileInfo = new File();
            fileInfo.setFileName(command.getFileName());
            fileInfo.setContentType(command.getContentType());
            fileInfo.setSize(command.getSize());
            fileInfo.setUser(Context.getUser());
            fileInfo.setFilePath(path.toString());
            fileInfo.setExtension(StringUtils.getFilenameExtension(path.toString()));

            // save file info to db
            // return file info
            return ObjectData.mapTo(fileRepository.save(fileInfo), FileDto.class);
        }
    }

    @Override
    public Resource downloadFile(UUID id) throws IOException {

        File fileInfo = getById(id, File.class);

        if (Objects.isNull(fileInfo)) {
            throw new DataNotFoundException("File not found");
        }

        Path filePath = Paths.get(fileInfo.getFilePath());

        if (!Files.exists(filePath)) {
            throw new DataNotFoundException("File not found");
        }

        return new ByteArrayResource(Files.readAllBytes(filePath));
    }

    @Override
    public FileDto createFileInfo(String fileUrl) throws IOException {

        if (Objects.isNull(fileUrl) || fileUrl.isEmpty()) return null;

        URL url = new URL(fileUrl);

        String fileName = FilenameUtils.getName(url.getPath());
//        String fileExtension = FilenameUtils.getExtension(url.getPath());


//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
//        String contentType = connection.getContentType();
//        System.out.println("Content Type: " + contentType);
//        connection.disconnect();

        FileTypeMap mimeTypesMap = MimetypesFileTypeMap.getDefaultFileTypeMap();
        String contentType = mimeTypesMap.getContentType(fileName);

        Path path = this.rootLocation.resolve(Paths.get(StorageUtils.buildDateFilePath(), fileName)).normalize();

        if (!path.toString().startsWith(this.rootLocation.toString())) {
            throw new StorageException("Cannot save file outside root directory");
        }

//        FileUtils.copyURLToFile(new URL(fileUrl), new java.io.File(path.toString()));

        return saveFileInfo(url.openStream(), path, fileName, contentType);
    }

    private FileDto saveFileInfo(InputStream url, Path path, String fileName, String contentType) throws IOException {
        try (InputStream inputStream = url) {
            long fileSize = StorageUtils.createFile(inputStream, path);

            // save file info to db
            File fileInfo = new File();
            fileInfo.setFileName(fileName);
            fileInfo.setContentType(contentType);
            fileInfo.setSize(fileSize);
            fileInfo.setUser(Context.getUser());
            fileInfo.setFilePath(path.toString());
            fileInfo.setExtension(StringUtils.getFilenameExtension(path.toString()));

            return ObjectData.mapTo(fileRepository.save(fileInfo), FileDto.class);
        }
    }
}
