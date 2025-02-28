package com.nexacloud.demoappinsights.exception;

/**
 * @author Priyonuj Dey
 */

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a business rule is violated.
 * Will result in a 409 Conflict HTTP response.
 */
public class BusinessRuleViolationException extends BaseAppInsightsException {

    public BusinessRuleViolationException(String message) {
        super(message, HttpStatus.CONFLICT);
        withProperty("businessRule", "violated");
    }
}