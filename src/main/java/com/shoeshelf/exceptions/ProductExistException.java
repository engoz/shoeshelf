package com.shoeshelf.exceptions;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String productIsExist) {
        super(productIsExist);
    }
}
