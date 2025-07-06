package com.projectmanagement.controlleradvice;

public class SqlException extends RuntimeException {
    public SqlException(String message) {
        super(message);
    }
}
