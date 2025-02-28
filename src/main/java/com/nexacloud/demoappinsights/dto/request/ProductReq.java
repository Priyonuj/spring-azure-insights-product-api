package com.nexacloud.demoappinsights.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new product.
 *
 * This class defines the structure of the request body for creating a new product.
 * It includes fields for the product name, description, and price.
 *
 * @author Priyonuj Dey
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    @NotBlank(message = "Product name is required")
    @Schema(description = "Product name", example = "Product 1")
    private String name;

    @NotBlank(message = "Product description is required")
    @Schema(description = "Product description", example = "Product description")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    @Schema(description = "Product price", example = "10.99")
    private Double price;
}
