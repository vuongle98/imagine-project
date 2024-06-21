package com.vuongle.imaginepg.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadCommand {

    private MultipartFile file;
    private int chunk;
    private int totalChunks;
    private String identifier;
    private String fileName;
    private int size;
    private String contentType;
}
