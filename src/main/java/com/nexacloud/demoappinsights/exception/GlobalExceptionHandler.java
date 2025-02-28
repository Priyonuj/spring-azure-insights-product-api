package com.nexacloud.demoappinsights.exception;

import com.nexacloud.demoappinsights.dto.response.ErrorRes;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler class
 *
 * <p>
 * This class handles exceptions that occur during the execution of the application.
 * It tracks exceptions in Application Insights and returns appropriate error responses.
 * </p>
 * @author Priyonuj Dey
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final TelemetryUtil telemetryUtil;

    /**
     * Handles custom application exceptions that integrate with Application Insights.
     *
     * @param ex The BaseAppInsightsException or subclass
     * @return ResponseEntity with the appropriate status code and error message
     */
    @ExceptionHandler(BaseAppInsightsException.class)
    public ResponseEntity<ErrorRes> handleBaseAppInsightsException(BaseAppInsightsException ex) {
        // Track the exception in Application Insights
        ex.track(null); // The exception tracks itself

        // Get exception type name (without package prefix)
        String exceptionType = ex.getClass().getSimpleName();

        // Create error response
        ErrorRes errorResponse = new ErrorRes(
                exceptionType + ": " + ex.getMessage(),
                ex.getHttpStatus().getReasonPhrase(),
                ex.getHttpStatus().toString()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Track validation error
        Map<String, String> properties = new HashMap<>();
        properties.put("errorType", "ValidationError");
        properties.put("errorCount", String.valueOf(errors.size()));
        properties.put("fields", errors.keySet().stream().collect(Collectors.joining(",")));

        telemetryUtil.trackOperation("RequestValidationFailed", properties, System.currentTimeMillis(), null);

        // Compose a single validation error message with exception type
        String message = "ValidationException: Validation failed for fields: " + String.join(", ", errors.keySet());

        ErrorRes errorResponse = new ErrorRes(
                message,
                "Validation Error",
                "400"
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleGenericExceptions(Exception ex) {
        // Track the exception
        Map<String, String> properties = new HashMap<>();
        properties.put("exceptionType", ex.getClass().getName());
        telemetryUtil.trackException(ex, properties);

        // Get exception type name (without package prefix)
        String exceptionType = ex.getClass().getSimpleName();

        ErrorRes errorResponse = new ErrorRes(
                exceptionType + ": An unexpected error occurred",
                "Internal Server Error",
                "500"
        );

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}