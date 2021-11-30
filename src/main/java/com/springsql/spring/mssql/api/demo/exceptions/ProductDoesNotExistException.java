package com.springsql.spring.mssql.api.demo.exceptions;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException() {
        super("Product does not exist");
    }

    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
