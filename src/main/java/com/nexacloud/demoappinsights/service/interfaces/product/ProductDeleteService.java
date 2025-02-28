package com.nexacloud.demoappinsights.service.interfaces.product;

/**
 * ProductDeleteService interface
 *
 * <p>
 * This interface defines methods for deleting products from the database.
 * </p>
 *
 * @author Priyonuj Dey
 */
public interface ProductDeleteService {

    /**
     * Deletes a product by its ID.
     *
     * <p>
     * This method deletes the product with the specified ID from the database.
     * </p>
     *
     * @param id The ID of the product to delete
     */
    void deleteProduct(Long id);
}
