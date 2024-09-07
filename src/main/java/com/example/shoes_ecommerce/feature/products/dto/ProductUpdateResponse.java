package com.example.shoes_ecommerce.feature.products.dto;

import com.example.shoes_ecommerce.feature.categories.dto.CategoriesResponse;

public record ProductUpdateResponse(
        String name,

        String description,

        Integer discount,

        Integer stock,

        Integer unitPrice,

        String uuid ,

        Boolean status ,

        CategoriesResponse categories
) {
}
