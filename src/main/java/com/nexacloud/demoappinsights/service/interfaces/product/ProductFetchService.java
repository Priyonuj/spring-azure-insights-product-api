package com.nexacloud.demoappinsights.service.interfaces.product;


import com.nexacloud.demoappinsights.entity.ProductModel;

import java.util.List;
import java.util.Optional;

/**
 * ProductFetchService interface
 *
 * <p>
 * This interface defines methods for fetching products from the database.
 * It provides methods for retrieving all products, products by minimum price, and individual products.
 * </p>
 *
 * @author Priyonuj Dey
 */
public interface ProductFetchService {
    /**
     * Retrieves a list of all products in the database.
     *
     * <p>
     * This method retrieves all products from the database and returns them as a list.
     * </p>
     *
     * @return A list of ProductModel entities
     */
    List<ProductModel> getAllProducts();

    /**
     * Retrieves a list of products with a minimum price.
     *
     * <p>
     * This method retrieves products from the database with a minimum price and returns them as a list.
     * </p>
     *
     * @param minPrice The minimum price of the products to retrieve
     * @return A list of ProductModel entities
     */
    List<ProductModel> getProductsByMinPrice(Double minPrice);

    /**
     * Retrieves a product by its ID.
     *
     * <p>
     * This method retrieves a product from the database with the specified ID and returns it as an Optional.
     * </p>
     *
     * @param id The ID of the product to retrieve
     * @return An Optional containing the ProductModel entity
     */
    Optional<ProductModel> getProductById(Long id);


}
