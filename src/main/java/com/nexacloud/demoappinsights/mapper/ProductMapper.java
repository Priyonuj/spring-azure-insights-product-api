package com.nexacloud.demoappinsights.mapper;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Utility class for mapping between Product DTOs and entities.
 *
 * @author Refactored from original code
 */
@Component
public class ProductMapper {

    /**
     * Maps a ProductReq DTO to a new ProductModel entity.
     *
     * @param productReq The product request DTO
     * @return A new ProductModel with fields populated from the DTO
     */
    public ProductModel toEntity(ProductReq productReq) {
        ProductModel productModel = new ProductModel();
        productModel.setName(productReq.getName());
        productModel.setDescription(productReq.getDescription());
        productModel.setPrice(productReq.getPrice());
        productModel.setCreatedAt(LocalDateTime.now());
        productModel.setUpdatedAt(LocalDateTime.now());
        return productModel;
    }

    /**
     * Updates an existing ProductModel's fields with values from a ProductReq DTO.
     *
     * @param existingProduct The existing product entity to update
     * @param updatedProduct The product request DTO containing new values
     * @return The updated ProductModel entity
     */
    public ProductModel updateEntityFromDto(ProductModel existingProduct, ProductReq updatedProduct) {
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        return existingProduct;
    }
}