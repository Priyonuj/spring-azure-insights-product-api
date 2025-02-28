package com.nexacloud.demoappinsights.exception;

/**
 * @author Priyonuj Dey
 */

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a validation error occurs.
 * Will result in a 400 Bad Request HTTP response.
 */
public class ValidationException extends BaseAppInsightsException {

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String field, String message) {
        super(message, HttpStatus.BAD_REQUEST);
        withProperty("field", field);
        withProperty("validationError", message);
    }

    @Override
    public ValidationException withProperty(String key, String value) {
        super.withProperty(key, value);
        return this;
    }
}