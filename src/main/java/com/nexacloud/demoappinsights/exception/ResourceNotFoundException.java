package com.nexacloud.demoappinsights.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found.
 * Will result in a 404 Not Found HTTP response.
 */
@Getter
public class ResourceNotFoundException extends BaseAppInsightsException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format("%s with ID %s not found", resourceType, resourceId), HttpStatus.NOT_FOUND);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        withProperty("resourceType", resourceType);
        withProperty("resourceId", resourceId);
    }
}