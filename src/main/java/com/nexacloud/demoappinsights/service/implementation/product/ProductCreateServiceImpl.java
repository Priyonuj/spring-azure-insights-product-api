package com.nexacloud.demoappinsights.service.implementation.product;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;
import com.nexacloud.demoappinsights.exception.ValidationException;
import com.nexacloud.demoappinsights.repository.ProductRepository;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductCreateService;
import com.nexacloud.demoappinsights.util.ProductUtil;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ProductCreateServiceImpl class
 *
 * <p>
 * This class implements the ProductCreateService interface and provides methods for creating new products in the database.
 * It uses the ProductMapper to map between ProductReq DTOs and ProductModel entities.
 * </p>
 *
 * @author Priyonuj Dey
 */
@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {
    private final ProductRepository productRepository;
    private final TelemetryUtil telemetryUtil;


    /**
     * Creates a new product.
     *
     * <p>
     * This method maps the ProductReq DTO to a new ProductModel entity.
     * It then saves the entity to the database.
     * </p
     *
     * @param product The product details to create
     * @return A new ProductModel with fields populated from the DTO
     */
    @Override
    public ProductModel createProduct(ProductReq product) {
        if (product.getPrice() < 0) {
            ValidationException exception = new ValidationException("price", "Price cannot be negative")
                    .withProperty("attemptedPrice", String.valueOf(product.getPrice()));

            Map<String, String> properties = new HashMap<>();
            properties.put("operation", "createProduct");
            properties.put("productName", product.getName());
            properties.put("attemptedPrice", String.valueOf(product.getPrice()));

            telemetryUtil.trackException(exception, properties);
            throw exception;
        }

        long startTime = System.currentTimeMillis();
        ProductModel productModel = ProductUtil.mapToProductModel(product);

        Map<String, String> properties = new HashMap<>();
        properties.put("productName", productModel.getName());
        properties.put("operation", "createProduct");

        try {
            ProductModel savedProduct = productRepository.save(productModel);
            telemetryUtil.trackOperation("ProductCreated", properties, startTime, product.getPrice());
            return savedProduct;
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

}
