package com.shoeshelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotExistException extends Exception {

    public ProductNotExistException(String message) {
        super(message);
    }
}
