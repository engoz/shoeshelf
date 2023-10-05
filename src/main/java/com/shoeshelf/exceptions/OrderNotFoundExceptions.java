package com.shoeshelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundExceptions extends RuntimeException {

    public OrderNotFoundExceptions(String message) {
        super(message);
    }

}
