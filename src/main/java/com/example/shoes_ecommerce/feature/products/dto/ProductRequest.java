package com.example.shoes_ecommerce.feature.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "Description  is required")
        String description,

        @NotNull(message = "Discount is required")
        Integer discount,

        @NotNull(message = "Stock is required")
        Integer stock,

        @NotNull(message = "Unit Price is required")
        Integer unitPrice,

        @NotBlank(message = "Categories name is required")
        String   categoriesUuid

) {
}
