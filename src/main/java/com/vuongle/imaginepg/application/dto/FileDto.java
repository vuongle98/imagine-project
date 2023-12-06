package com.vuongle.imaginepg.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class FileDto implements Serializable {

    private UUID id;
    private String filename;
    private String extension;
    private long size;

    private UserDto user;
}
