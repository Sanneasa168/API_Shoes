package com.example.shoes_ecommerce.feature.auth.dto;

import lombok.Builder;

@Builder
public record ChangePasswordResponse(

        String message,

        String email,

        String accessToken
) {
}
