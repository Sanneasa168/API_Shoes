package com.example.shoes_ecommerce.feature.auth.dto;

import lombok.Builder;

@Builder
public record ResetPasswordResponse(

        String message ,

        String email
) {
}
