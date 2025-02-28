package com.nexacloud.demoappinsights.util;

import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Application Insights telemetry operations.
 * Centralizes common telemetry patterns used throughout the application.
 *
 * @author Priyonuj Dey
 */
@Component
public class TelemetryUtil {

    private final TelemetryClient telemetryClient;

    public TelemetryUtil(TelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

    /**
     * Tracks an operation with performance metrics.
     *
     * @param eventName Event name to track
     * @param properties Properties to add to the event
     * @param startTime Operation start time in milliseconds
     * @param resultCount Number of results returned (optional)
     */
    public void trackOperation(String eventName, Map<String, String> properties, long startTime, Double resultCount) {
        Map<String, Double> metrics = new HashMap<>();
        metrics.put("processingTimeMs", (double) (System.currentTimeMillis() - startTime));

        if (resultCount != null) {
            metrics.put("resultCount", resultCount);
        }

        telemetryClient.trackEvent(eventName, properties, metrics);
    }

    /**
     * Tracks an operation with performance metrics.
     *
     * @param metricName Metric name to track
     * @param processingTime Processing time in milliseconds
     */
    public void trackPerformance(String metricName, double processingTime) {
        telemetryClient.trackMetric(metricName, processingTime);
    }

    /**
     * Tracks an exception with context properties.
     *
     * @param exception Exception to track
     * @param properties Properties providing context about the exception
     */
    public void trackException(Exception exception, Map<String, String> properties) {
        telemetryClient.trackException(exception, properties, null);
    }

    /**
     * Creates a property map with endpoint information.
     *
     * @param method HTTP method (GET, POST, etc.)
     * @param path API path
     * @param entityId Optional entity ID
     * @return Map of properties
     */
    public Map<String, String> createEndpointProperties(String method, String path, String entityId) {
        Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", method + " " + path);

        if (entityId != null) {
            properties.put("entityId", entityId);
        }

        return properties;
    }
}