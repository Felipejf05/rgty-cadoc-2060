package com.rgty.cadoc2060.exception;

public class CadocFileNotFoundException extends RuntimeException{
    public CadocFileNotFoundException(String message, Long id){
        super(String.format(message, id));
    }
}
