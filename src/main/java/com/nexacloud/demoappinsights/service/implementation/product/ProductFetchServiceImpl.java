package com.nexacloud.demoappinsights.service.implementation.product;

import com.nexacloud.demoappinsights.entity.ProductModel;
import com.nexacloud.demoappinsights.exception.ResourceNotFoundException;
import com.nexacloud.demoappinsights.repository.ProductRepository;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductFetchService;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ProductFetchServiceImpl class
 *
 * <p>
 * This class implements the ProductFetchService interface and provides methods for fetching products from the database.
 * It uses the ProductRepository to retrieve products from the database.
 * </p>
 *
 * @author Priyonuj Dey
 */
@Service
@RequiredArgsConstructor
public class ProductFetchServiceImpl implements ProductFetchService {
    private final ProductRepository productRepository;
    private final TelemetryUtil telemetryUtil;

    /**
     * Retrieves a list of all products in the database.
     *
     * <p>
     * This method retrieves all products from the database and returns them as a list.
     * </p>
     *
     * @return A list of ProductModel entities
     */
    @Override
    public List<ProductModel> getAllProducts() {
        long startTime = System.currentTimeMillis();

        try {
            List<ProductModel> products = productRepository.findAll();
            telemetryUtil.trackPerformance("ProductListingPerformance", System.currentTimeMillis() - startTime);
            return products;
        } catch (Exception e) {
            telemetryUtil.trackException(e, Map.of("operation", "getAllProducts"));
            throw e;
        }
    }

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
    @Override
    public List<ProductModel> getProductsByMinPrice(Double minPrice) {
        long startTime = System.currentTimeMillis();
        Map<String, String> properties = new HashMap<>();
        properties.put("minPrice", minPrice.toString());
        properties.put("operation", "getProductsByMinPrice");

        try {
            List<ProductModel> products = productRepository.findByPriceGreaterThanEqual(minPrice);
            telemetryUtil.trackOperation("ProductFilteredByPrice", properties, startTime, (double) products.size());
            return products;
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

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
    @Override
    public Optional<ProductModel> getProductById(Long id) {
        Map<String, String> properties = new HashMap<>();
        properties.put("productId", id.toString());
        properties.put("operation", "getProductById");

        try {
            Optional<ProductModel> product = productRepository.findById(id);

            if (product.isPresent()) {
                telemetryUtil.trackOperation("ProductFound", properties, System.currentTimeMillis(), null);
            } else {
                telemetryUtil.trackOperation("ProductNotFound", properties, System.currentTimeMillis(), null);
                throw new ResourceNotFoundException("Product", id.toString());
            }

            return product;
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

}
