package com.nexacloud.demoappinsights.exception;

/**
 * @author Priyonuj Dey
 */
import com.microsoft.applicationinsights.TelemetryClient;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Base exception class that automatically tracks exceptions in Application Insights.
 * Provides common functionality for all application-specific exceptions.
 */
@Getter
public abstract class BaseAppInsightsException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Map<String, String> properties = new HashMap<>();

    /**
     * Creates a new exception with the given message and HTTP status.
     *
     * @param message The error message
     * @param httpStatus The HTTP status code to return
     */
    protected BaseAppInsightsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * Creates a new exception with the given message, cause, and HTTP status.
     *
     * @param message The error message
     * @param cause The cause of this exception
     * @param httpStatus The HTTP status code to return
     */
    protected BaseAppInsightsException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    /**
     * Adds a custom property to be tracked with this exception.
     *
     * @param key The property key
     * @param value The property value
     * @return This exception instance for method chaining
     */
    public BaseAppInsightsException withProperty(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Tracks this exception in Application Insights.
     *
     * @param telemetryClient The Application Insights telemetry client
     */
    public void track(TelemetryClient telemetryClient) {
        telemetryClient.trackException(this, this.properties, null);
    }
}