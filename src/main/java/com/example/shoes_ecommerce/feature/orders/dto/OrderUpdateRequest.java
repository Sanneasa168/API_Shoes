package com.example.shoes_ecommerce.feature.orders.dto;

import jakarta.validation.constraints.NotNull;

public record OrderUpdateRequest(

        @NotNull(message = "Total is required")
        Double totalPrice ,

        @NotNull(message = "Quantity is required ")
        Integer quantity
) {
}
