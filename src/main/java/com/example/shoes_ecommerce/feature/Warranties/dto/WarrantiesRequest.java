package com.example.shoes_ecommerce.feature.Warranties.dto;

import jakarta.validation.constraints.NotBlank;

public record WarrantiesRequest(

         @NotBlank(message = "Warranty productUuid is required ")
         String productsUuid
) {
}
