package com.nexacloud.demoappinsights.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Priyonuj Dey
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response object containing operation result details")
public class SuccessRes<T> {
    @Schema(description = "Message describing the result of the operation")
    private String message;

    @Schema(description = "HTTP status code of the response")
    private Integer statusCode;

    @Schema(description = "Data of any type returned by the operation")
    private T data;

}
