package com.example.shoes_ecommerce.feature.payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentCreateRequest(

        @NotBlank(message = "Order Uuid is required")
        String orderUuid,

        @NotNull(message = "Amount is required ")
        double amount
) {

}
