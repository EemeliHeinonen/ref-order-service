package com.eemeli.orderservice.model;

import org.springframework.http.HttpStatus;

public enum APIError {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 4000101, "Validation error, check the submitted fields"),
    BAD_FORMAT(HttpStatus.BAD_REQUEST, 4000102, "The request format is incorrect"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 4040103, "No element found");

    private final HttpStatus status;
    private final int code;
    private final String message;

    APIError(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
