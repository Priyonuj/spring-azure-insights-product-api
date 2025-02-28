package com.nexacloud.demoappinsights.service.interfaces.product;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;

/**
 * ProductCreateService interface
 *
 * <p>
 * This interface defines methods for creating new products in the database.
 * It provides methods for mapping between ProductReq DTOs and ProductModel entities.
 * </p>
 *
 * @author Priyonuj Dey
 */
public interface ProductCreateService {

    /**
     * Creates a new product.
     *
     * <p>
     * This method maps the ProductReq DTO to a new ProductModel entity.
     * It then saves the entity to the database.
     * </p>
     *
     * @param product The product details to create
     * @return A new ProductModel with fields populated from the DTO
     */
    ProductModel createProduct(ProductReq product);


}
