package com.nexacloud.demoappinsights.util;

import com.nexacloud.demoappinsights.dto.response.SuccessRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Utility class for building standardized API responses.
 * Centralizes response creation logic to maintain consistency across the application.
 *
 * @author Priyonuj Dey
 */
@Component
public class ResponseUtil {

    /**
     * Creates a success response with HTTP 200 OK status.
     *
     * @param message Success message
     * @param data Response data
     * @param <T> Type of response data
     * @return ResponseEntity with standard success format
     */
    public <T> ResponseEntity<SuccessRes<T>> createOkResponse(String message, T data) {
        return ResponseEntity.ok(new SuccessRes<>(message, HttpStatus.OK.value(), data));
    }

    /**
     * Creates a success response with HTTP 201 CREATED status.
     *
     * @param message Success message
     * @param data Response data
     * @param <T> Type of response data
     * @return ResponseEntity with standard success format
     */
    public <T> ResponseEntity<SuccessRes<T>> createCreatedResponse(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessRes<>(message, HttpStatus.CREATED.value(), data));
    }

    /**
     * Creates a success response with HTTP 202 ACCEPTED status.
     *
     * @param message Success message
     * @param data Response data
     * @param <T> Type of response data
     * @return ResponseEntity with standard success format
     */
    public <T> ResponseEntity<SuccessRes<T>> createAcceptedResponse(String message, T data) {
        return ResponseEntity.accepted()
                .body(new SuccessRes<>(message, HttpStatus.ACCEPTED.value(), data));
    }
}