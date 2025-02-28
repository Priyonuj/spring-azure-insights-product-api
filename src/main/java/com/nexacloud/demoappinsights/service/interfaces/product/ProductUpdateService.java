package com.nexacloud.demoappinsights.service.interfaces.product;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;

import java.util.Optional;

/**
 * ProductUpdateService interface
 *
 * <p>
 * This interface defines methods for updating products in the database.
 * It provides methods for updating existing products and retrieving product details by ID.
 * </p>
 *
 * @author Priyonuj Dey
 */
public interface ProductUpdateService {
    /**
     * Updates an existing product.
     *
     * <p>
     * This method updates the product with the specified ID in the database.
     * It returns an Optional containing the updated product.
     * </p>
     *
     * @param id The ID of the product to update
     * @param product The updated product details
     * @return An Optional containing the updated ProductModel entity
     */
    Optional<ProductModel> updateProduct(Long id, ProductReq product);

}
