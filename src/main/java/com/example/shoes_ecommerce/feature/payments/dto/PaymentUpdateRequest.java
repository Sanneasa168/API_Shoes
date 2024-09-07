package com.example.shoes_ecommerce.feature.payments.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentUpdateRequest(

        @NotNull(message = "Amount is required ")
        double amount
) {
}
