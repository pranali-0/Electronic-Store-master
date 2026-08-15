package com.icwd.electronic.store.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
    private String resource;
    private String userid;

    public ResourceNotFoundException() {
        super("Resource Not Found ..");

    }
    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String resource , String userid){
        super(String.format(("%s not Found with  : %s"),resource,userid));
    }


}

