package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.FileUploadCommand;
import com.vuongle.imaginepg.application.dto.FileDto;
import com.vuongle.imaginepg.application.queries.FileFilter;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService extends BaseService<FileDto, FileUploadCommand, FileFilter> {

    FileDto uploadFile(MultipartFile file) throws IOException;

    Resource downloadFile(UUID id) throws IOException;

}
