package com.example.shoes_ecommerce.feature.shop_infos.dto;

import jakarta.validation.constraints.NotBlank;

public record ShopInfoRequest(

        @NotBlank(message = "Shop name is required")
        String name ,

        @NotBlank(message = "Shop address is required")
        String address,

        @NotBlank(message = "Shop email is required")
        String email ,

        @NotBlank(message = "Shop phone number is required")
        String phone,

        @NotBlank(message = "Shop logo is required")
        String logo
) {
}
