package com.nexacloud.demoappinsights.service.implementation.product;

import com.nexacloud.demoappinsights.exception.ResourceNotFoundException;
import com.nexacloud.demoappinsights.repository.ProductRepository;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductDeleteService;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ProductDeleteServiceImpl class
 *
 * <p>
 * This class implements the ProductDeleteService interface and provides methods for deleting products from the database.
 * </p>
 *
 * @author Priyonuj Dey
 */
@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {
    private final ProductRepository productRepository;
    private final TelemetryUtil telemetryUtil;


    /**
     * Deletes a product by its ID.
     *
     * <p>
     * This method deletes the product with the specified ID from the database.
     * </p>
     *
     * @param id The ID of the product to delete
     */
    @Override
    public void deleteProduct(Long id) {
        Map<String, String> properties = new HashMap<>();
        properties.put("productId", id.toString());
        properties.put("operation", "deleteProduct");

        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                telemetryUtil.trackOperation("ProductDeleted", properties, System.currentTimeMillis(), null);
            } else {
                telemetryUtil.trackOperation("ProductDeleteFailed_NotFound", properties, System.currentTimeMillis(), null);
                throw new ResourceNotFoundException("Product", id.toString());
            }
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }
}
