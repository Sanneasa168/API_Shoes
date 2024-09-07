package com.example.shoes_ecommerce.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(

        @NotBlank(message = "Email is required ")
        String email,

        @NotBlank(message = "Verified is required ")
        String verifiedCode
) {
}
