package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.FileUploadCommand;
import com.vuongle.imaginepg.application.dto.FileDto;
import com.vuongle.imaginepg.application.queries.FileFilter;
import com.vuongle.imaginepg.domain.services.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(
            FileService fileService
    ) {
        this.fileService = fileService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<FileDto>> searchFiles(
            FileFilter filter,
            Pageable pageable
    ) {
        Page<FileDto> filePage = fileService.getPageable(filter, pageable);

        return ResponseEntity.ok(filePage);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<FileDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        FileDto file = fileService.getById(id);

        return ResponseEntity.ok(file);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<FileDto> upload(
            @RequestParam(value = "file") MultipartFile file
    ) throws IOException {
        FileDto fileInfo = fileService.uploadFile(file);

        return ResponseEntity.ok(fileInfo);
    }

    @PostMapping("/chunk")
    public ResponseEntity<Boolean> uploadStream(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "chunk") int chunk,
            @RequestParam(value = "totalChunks") int totalChunks,
            @RequestParam(value = "identifier") String identifier
    ) throws IOException {

        FileUploadCommand command = new FileUploadCommand();
        command.setFile(file);
        command.setChunk(chunk);
        command.setIdentifier(identifier);
        command.setTotalChunks(totalChunks);

        Boolean upload = fileService.uploadFileChunk(command);

        return ResponseEntity.ok(upload);
    }

    @PostMapping("/chunk/merge")
    public ResponseEntity<FileDto> mergeChunk(
            @RequestBody FileUploadCommand command
    ) throws IOException {

        FileDto fileInfo = fileService.mergeFileChunk(command);

        return ResponseEntity.ok(fileInfo);
    }

    @GetMapping("/{id}/download")
    @SecurityRequirement(name = "Bearer authentication")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Object> download(
            @PathVariable(value = "id") UUID id
    ) throws IOException {
        FileDto fileInfo = fileService.getById(id);

        Resource fileResource = fileService.downloadFile(id);
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileInfo.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(fileResource);
    }
}
