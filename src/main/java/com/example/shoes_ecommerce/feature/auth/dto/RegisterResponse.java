package com.example.shoes_ecommerce.feature.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(

        String message ,

        String  email


) {
}
