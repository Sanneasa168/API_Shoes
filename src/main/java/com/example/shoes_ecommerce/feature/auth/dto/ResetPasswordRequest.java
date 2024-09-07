package com.example.shoes_ecommerce.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(

        @NotBlank(message= "New password is required ")
        String newPassword,

        @NotBlank(message= "ConfirmPassword is required ")
        String confirmPassword,

        String resetToken

) {
}
