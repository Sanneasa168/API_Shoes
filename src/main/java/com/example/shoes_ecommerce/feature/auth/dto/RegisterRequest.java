package com.example.shoes_ecommerce.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message= "First name is required ")
        String  firstName,

        @NotBlank(message= "Last name is required ")
        String lastName,

        @NotBlank(message= "Email is required ")
        String  email,

        @NotBlank(message= "Gender is required ")
        String  gender,

        @NotBlank(message= "Password is required ")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
                String password,

        @NotBlank(message= "Confirm password is required ")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "ConfirmPassword must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
        String confirmPassword,

        @NotBlank(message= "Phone number is required ")
                @Size(min = 9 ,max= 10 ,message = "Phone number must between 9 to 10 digits")
        String phoneNumber,

        @NotBlank(message= "Address is required ")
        String  address
) {
}
