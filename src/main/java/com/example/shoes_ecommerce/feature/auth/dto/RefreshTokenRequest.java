package com.example.shoes_ecommerce.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

        @NotBlank(message = "Refresh token is required ")
        String refreshToken
) {
}
