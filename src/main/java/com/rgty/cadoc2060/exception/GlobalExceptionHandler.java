package com.rgty.cadoc2060.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CadocFileNotFoundException.class)
    public ResponseEntity<String>handleCadocFileNotFoundException(CadocFileNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<String>handleFileValidationException(FileValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(FileReadingException.class)
    public ResponseEntity<String>handleFileReadingException(FileReadingException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String>handleInternalServerException(InternalServerException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
