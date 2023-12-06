package com.vuongle.imaginepg.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorDetail implements Serializable {

    private String message;
    private Instant time;
    private String details;
}
