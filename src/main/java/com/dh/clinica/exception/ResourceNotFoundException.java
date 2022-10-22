package com.dh.clinica.exception;

public class ResourceNotFoundException extends RuntimeException {

    private int id;

    public ResourceNotFoundException (String message) {
        super(message);
    }

    public ResourceNotFoundException (int id, String message) {
        super(message);
        this.id = id;
    }
}
