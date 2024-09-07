package com.example.shoes_ecommerce.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(

        String tokenType ,

        String  accessToken ,

        String refreshToken
) {
}
