package com.eemeli.orderservice.exception;

import com.eemeli.orderservice.dto.ErrorDTO;
import com.eemeli.orderservice.model.APIError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdviceException {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdviceException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO validationFailureHandler(MethodArgumentNotValidException exception) {
        var errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::formatErrorMessage)
                .toList();

        var error = new ErrorDTO(
                APIError.VALIDATION_ERROR.getCode(),
                APIError.VALIDATION_ERROR.getMessage(),
                errors
        );

        logger.error("Validation failure - {}", error);

        return error;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO badRequestHandler(HttpMessageNotReadableException exception) {
        var error = new ErrorDTO(
                APIError.BAD_FORMAT.getCode(),
                APIError.BAD_FORMAT.getMessage(),
                List.of()
        );

        logger.error("Bad request - {}", error);

        return error;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO notFoundHandler(NoSuchElementException exception) {
        var error = new ErrorDTO(
                APIError.NOT_FOUND.getCode(),
                APIError.NOT_FOUND.getMessage(),
                List.of(exception.getMessage())
        );

        logger.error("Element not found - {}", error);

        return error;
    }

    private String formatErrorMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
        }
    }
}
