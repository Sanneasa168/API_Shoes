package com.example.shoes_ecommerce.feature.categories.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriesRequest(

        @NotBlank (message = "Name is required")
        String name

) {
}
