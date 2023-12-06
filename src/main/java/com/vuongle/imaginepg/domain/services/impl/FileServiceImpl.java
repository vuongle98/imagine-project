package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.FileUploadCommand;
import com.vuongle.imaginepg.application.dto.FileDto;
import com.vuongle.imaginepg.application.queries.FileFilter;
import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.repositories.FileRepository;
import com.vuongle.imaginepg.domain.services.FileService;
import com.vuongle.imaginepg.infrastructure.specification.FileSpecifications;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(
            FileRepository fileRepository
    ) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDto getById(UUID id) {
        return ObjectData.mapTo(fileRepository.getById(id), FileDto.class);
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

    }

    @Override
    public Page<FileDto> getAll(FileFilter filter, Pageable pageable) {
        Specification<File> specification = FileSpecifications.withFilter(filter);
        Page<File> filePage = fileRepository.findAll(specification, pageable);
        return filePage.map(file -> ObjectData.mapTo(file, FileDto.class));
    }

    @Override
    public List<FileDto> getAll(FileFilter filter) {
        Specification<File> specification = FileSpecifications.withFilter(filter);
        return ObjectData.mapListTo(fileRepository.findAll(specification), FileDto.class);
    }

    @Override
    public FileDto uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public Resource downloadFile(UUID id) {
        return null;
    }
}
