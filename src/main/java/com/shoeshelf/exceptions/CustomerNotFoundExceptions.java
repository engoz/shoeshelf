package com.shoeshelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundExceptions extends Throwable {
    public CustomerNotFoundExceptions(String message) {
        super(message);
    }
}
