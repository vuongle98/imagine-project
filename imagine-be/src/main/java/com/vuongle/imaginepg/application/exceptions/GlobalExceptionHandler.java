package com.vuongle.imaginepg.application.exceptions;

import com.vuongle.imaginepg.application.dto.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataExistedException.class)
    public ResponseEntity<ErrorDetail> handleExistedException(DataExistedException dataExistedException, WebRequest webRequest) {
        ErrorDetail error = new ErrorDetail(dataExistedException.getMessage(), Instant.now(), webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = new ArrayList<>();

//        Map<String, String> errs = new HashMap<>();
//
//        for (var err: ex.getFieldErrors()) {
//            errs.put(err.getField(), err.getDefaultMessage());
//        }

        for (var err : ex.getBindingResult().getAllErrors()) {
            errors.add(err.getDefaultMessage());
        }

        ErrorDetail errorResponse = new ErrorDetail(errors.toString(), Instant.now(), "An error occurred");

        return this.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> globalExceptionHandle(Exception exception, WebRequest webRequest) {
        ErrorDetail error = new ErrorDetail(exception.getMessage(), Instant.now(), webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
