package com.vuongle.imaginepg.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StorageException extends RuntimeException {

    public StorageException(String mess) {
        super(mess);
    }
}
