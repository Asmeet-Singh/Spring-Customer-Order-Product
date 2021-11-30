package com.springsql.spring.mssql.api.demo.exceptions;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String s) {
        super(s);
    }
}
