package com.nexacloud.demoappinsights.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Product entity class
 *
 * @author Priyonuj Dey
 */
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Product ID " ,example = "1")
    private Long id;

    @Schema(description = "Product name" ,example = "iPhone 14")
    @Column(nullable = false)
    private String name;

    @Schema(description = "Product description" ,example = "Apple iPhone 14 Pro Max 256GB")
    @Column(nullable = false)
    private String description;

    @Min(value = 0, message = "Price must be positive")
    @Schema(description = "Product price" ,example = "100.00")
    @Column(nullable = false)
    private Double price;

    @CreationTimestamp
    @Schema(description = "Date and time when the product was created" ,example = "2023-01-01T00:00:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(description = "Date and time when the product was last updated" ,example = "2023-01-01T00:00:00")
    private LocalDateTime updatedAt;
}
