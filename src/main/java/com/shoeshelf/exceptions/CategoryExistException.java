package com.shoeshelf.exceptions;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException(String message) {
        super(message);
    }

}
