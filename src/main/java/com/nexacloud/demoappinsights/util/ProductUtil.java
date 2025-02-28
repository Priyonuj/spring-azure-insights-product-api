package com.nexacloud.demoappinsights.util;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ProductUtil {
    /**
     * Maps a ProductReq DTO to a ProductModel entity.
     *
     * @param productReq The product request DTO
     * @return A new ProductModel with fields populated from the DTO
     */
    public ProductModel mapToProductModel(ProductReq productReq) {
        ProductModel productModel = new ProductModel();
        productModel.setPrice(productReq.getPrice());
        productModel.setName(productReq.getName());
        productModel.setDescription(productReq.getDescription());
        productModel.setCreatedAt(LocalDateTime.now());
        productModel.setUpdatedAt(LocalDateTime.now());
        return productModel;
    }

    /**
     * Updates an existing ProductModel's fields with values from a ProductReq DTO.
     *
     * @param existingProduct The existing product entity to update
     * @param updatedProduct The product request DTO containing new values
     */
    public void updateProductFields(ProductModel existingProduct, ProductReq updatedProduct) {
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setUpdatedAt(LocalDateTime.now());
    }
}
