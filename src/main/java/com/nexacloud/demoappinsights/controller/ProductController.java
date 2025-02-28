package com.nexacloud.demoappinsights.controller;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.dto.response.ErrorRes;
import com.nexacloud.demoappinsights.dto.response.SuccessRes;
import com.nexacloud.demoappinsights.entity.ProductModel;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductCreateService;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductDeleteService;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductFetchService;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductUpdateService;
import com.nexacloud.demoappinsights.util.ResponseUtil;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ProductController class
 *
 * <p>
 * This class provides endpoints for managing products in the application.
 * It uses the ProductService to perform CRUD operations on products.
 * </p>
 *
 * @author Priyonuj Dey
 */
@RestController
@RequestMapping("api/products")
@Tag(name = "Products", description = "Endpoints for managing products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDeleteService productDeleteService;
    private final ProductFetchService productFetchService;
    private final ProductCreateService productCreateService;
    private final ProductUpdateService productUpdateService;
    private final TelemetryUtil telemetryUtil;
    private final ResponseUtil responseUtil;

    /**
     * Retrieves a list of all products in the database.
     *
     * @param minPrice The minimum price of the products to retrieve (optional)
     * @return A ResponseEntity containing the list of products
     */
    @Operation(summary = "Get all products",
            description = "Retrieves a list of all products in the database.",
            tags = {"Products"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response with a list of products",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid input parameters",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class)))
            })
    @GetMapping
    public ResponseEntity<SuccessRes<List<ProductModel>>> getAllProducts(@RequestParam(required = false) Double minPrice) {
        Map<String, String> properties = telemetryUtil.createEndpointProperties("GET", "/api/products", null);
        properties.put("hasFilter", String.valueOf(minPrice != null));

        if (minPrice != null) {
            properties.put("minPrice", minPrice.toString());
        }

        try {
            List<ProductModel> products;
            if (minPrice != null) {
                telemetryUtil.trackOperation("ProductsListFiltered", properties, System.currentTimeMillis(), null);
                products = productFetchService.getProductsByMinPrice(minPrice);
            } else {
                telemetryUtil.trackOperation("ProductsListRequested", properties, System.currentTimeMillis(), null);
                products = productFetchService.getAllProducts();
            }

            return responseUtil.createOkResponse("Data fetched successfully", products);
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve
     * @return A ResponseEntity containing the product details
     */
    @Operation(summary = "Get product by ID",
            description = "Retrieves a product by its ID.",
            tags = {"Products"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response with the product details",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessRes.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessRes<ProductModel>> getProductById(@PathVariable Long id) {
        Map<String, String> properties = telemetryUtil.createEndpointProperties("GET", "/api/products/" + id, id.toString());

        try {
            telemetryUtil.trackOperation("ProductDetailsRequested", properties, System.currentTimeMillis(), null);
            Optional<ProductModel> product = productFetchService.getProductById(id);
            return responseUtil.createOkResponse("Data fetched successfully", product.get());
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

    /**
     * Creates a new product.
     *
     * @param product The product details to create
     * @return A ResponseEntity containing the created product
     */
    @Operation(summary = "Create product",
            description = "Creates a new product with the provided details.",
            tags = {"Products"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful response with the created product",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid input parameters",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class)))
            })
    @PostMapping
    public ResponseEntity<SuccessRes<ProductModel>> createProduct(@RequestBody @Valid ProductReq product) {
        Map<String, String> properties = telemetryUtil.createEndpointProperties("POST", "/api/products", null);
        properties.put("productName", product.getName());

        try {
            long startTime = System.currentTimeMillis();
            ProductModel createdProduct = productCreateService.createProduct(product);

            Map<String, Double> metrics = new HashMap<>();
            metrics.put("processingTimeMs", (double) (System.currentTimeMillis() - startTime));
            metrics.put("productPrice", product.getPrice());

            telemetryUtil.trackOperation("ProductCreated", properties, startTime, null);
            return responseUtil.createCreatedResponse("Product created successfully", createdProduct);
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update
     * @param product The updated product details
     * @return A ResponseEntity containing the updated product
     */
    @Operation(summary = "Update product",
            description = "Updates an existing product with the provided details.",
            tags = {"Products"},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Successful response with the updated product",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessRes.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid input parameters",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<SuccessRes<ProductModel>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductReq product) {
        Map<String, String> properties = telemetryUtil.createEndpointProperties("PUT", "/api/products/" + id, id.toString());
        properties.put("productName", product.getName());

        try {
            telemetryUtil.trackOperation("ProductUpdateRequested", properties, System.currentTimeMillis(), null);
            Optional<ProductModel> updatedProduct = productUpdateService.updateProduct(id, product);
            return responseUtil.createAcceptedResponse("Product updated successfully", updatedProduct.get());
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     * @return A ResponseEntity indicating the success or failure of the operation
     */
    @Operation(summary = "Delete product",
            description = "Deletes a product with the specified ID.",
            tags = {"Products"},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Successful response indicating the product was deleted",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessRes.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessRes<Void>> deleteProduct(@PathVariable Long id) {
        Map<String, String> properties = telemetryUtil.createEndpointProperties("DELETE", "/api/products/" + id, id.toString());

        try {
            telemetryUtil.trackOperation("ProductDeleteRequested", properties, System.currentTimeMillis(), null);
            productDeleteService.deleteProduct(id);
            return responseUtil.createAcceptedResponse("Product deleted successfully", null);
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }
}