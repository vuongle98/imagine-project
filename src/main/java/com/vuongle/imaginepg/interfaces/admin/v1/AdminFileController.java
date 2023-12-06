package com.vuongle.imaginepg.interfaces.admin.v1;

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
@RequestMapping("/api/admin/file")
public class AdminFileController {

    private final FileService fileService;

    public AdminFileController(
            FileService fileService
    ) {
        this.fileService = fileService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<Page<FileDto>> searchFiles(
            FileFilter filter,
            Pageable pageable
    ) {
        Page<FileDto> filePage = fileService.getAll(filter, pageable);

        return ResponseEntity.ok(filePage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<FileDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        FileDto file = fileService.getById(id);

        return ResponseEntity.ok(file);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<FileDto> upload(
            @RequestParam(value = "file") MultipartFile file
    ) {
        FileDto fileInfo = fileService.uploadFile(file);

        return ResponseEntity.ok(fileInfo);
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
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
