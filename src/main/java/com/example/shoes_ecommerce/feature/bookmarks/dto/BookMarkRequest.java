package com.example.shoes_ecommerce.feature.bookmarks.dto;

import jakarta.validation.constraints.NotBlank;

public record BookMarkRequest(

        @NotBlank(message ="Product Uuid is required ")
        String productUuid,

        @NotBlank(message ="Product Uuid is required ")
        String userUuid

) {
}
