package com.beautystor.exception;

public class ProductImageException extends RuntimeException {
    
    public ProductImageException(String message) {
        super(message);
    }

    public ProductImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
