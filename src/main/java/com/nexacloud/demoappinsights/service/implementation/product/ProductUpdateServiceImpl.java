package com.nexacloud.demoappinsights.service.implementation.product;

import com.nexacloud.demoappinsights.dto.request.ProductReq;
import com.nexacloud.demoappinsights.entity.ProductModel;
import com.nexacloud.demoappinsights.exception.ResourceNotFoundException;
import com.nexacloud.demoappinsights.repository.ProductRepository;
import com.nexacloud.demoappinsights.service.interfaces.product.ProductUpdateService;
import com.nexacloud.demoappinsights.util.ProductUtil;
import com.nexacloud.demoappinsights.util.TelemetryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ProductUpdateServiceImpl class
 *
 * <p>
 * This class implements the ProductUpdateService interface and provides methods for updating products in the database.
 * It uses the ProductRepository to retrieve existing products and update them.
 * </p>
 *
 * @author Priyonuj Dey
 */
@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {
    private final ProductRepository productRepository;
    private final TelemetryUtil telemetryUtil;

    /**
     * Updates an existing product.
     *
     * <p>
     * This method updates the product with the specified ID in the database.
     * It returns an Optional containing the updated product.
     * </p>
     *
     * @param id The ID of the product to update
     * @param updatedProduct The updated product details
     * @return An Optional containing the updated ProductModel entity
     */
    @Override
    public Optional<ProductModel> updateProduct(Long id, ProductReq updatedProduct) {
        Map<String, String> properties = new HashMap<>();
        properties.put("productId", id.toString());
        properties.put("productName", updatedProduct.getName());
        properties.put("operation", "updateProduct");

        try {
            Optional<ProductModel> existingProductOpt = productRepository.findById(id);

            if (existingProductOpt.isPresent()) {
                ProductModel product = existingProductOpt.get();
                ProductUtil.updateProductFields(product, updatedProduct);
                ProductModel saved = productRepository.save(product);
                telemetryUtil.trackOperation("ProductUpdated", properties, System.currentTimeMillis(), null);
                return Optional.of(saved);
            } else {
                telemetryUtil.trackOperation("ProductUpdateFailed_NotFound", properties, System.currentTimeMillis(), null);
                throw new ResourceNotFoundException("Product", id.toString());
            }
        } catch (Exception e) {
            telemetryUtil.trackException(e, properties);
            throw e;
        }
    }

}
