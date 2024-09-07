package com.example.shoes_ecommerce.feature.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(

        @NotNull(message = "Total price is required ")
        Double totalPrice ,

        @NotNull(message = "Quantity is required ")
        Integer quantity,

        @NotBlank(message = "User Uuid is required ")
        String  userUuid ,

        @NotNull(message = "Products Uuid is required ")
        List<String> productUuid

) {

}
