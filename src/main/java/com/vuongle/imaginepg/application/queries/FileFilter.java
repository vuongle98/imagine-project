package com.vuongle.imaginepg.application.queries;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FileFilter implements Serializable {

    private String likeName;

    private String extension;
}
