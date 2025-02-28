package com.nexacloud.demoappinsights.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response class
 *
 * <p>
 * This class represents an error response with a message, error code, and error type.
 * It is used to return error information to the client in case of an exception.
 * </p>
 *
 * @author Priyonuj Dey
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response object")
public class ErrorRes {
    @Schema(description = "Detailed error message including exception type")
    private String message;

    @Schema(description = "Type of error that occurred" )
    private String error;

    @Schema(description = "HTTP status code of the error response")
    private String errorCode;
}
