package com.nexacloud.demoappinsights.repository;

import com.nexacloud.demoappinsights.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product repository interface
 *
 * @author Priyonuj Dey
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByPriceGreaterThanEqual(Double minPrice);
}