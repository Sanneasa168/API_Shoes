package com.example.shoes_ecommerce.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SendVerificationRequest(

        @NotBlank(message = "Email is required ")
        String email
){
}
