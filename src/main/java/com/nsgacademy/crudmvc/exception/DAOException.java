package com.nsgacademy.crudmvc.exception;

public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
