package com.example.shoes_ecommerce.feature.orders.dto;


import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Double totalPrice,

        Integer quantity,

        Boolean isOrder,

        LocalDateTime orderDate ,

        List<ProductResponse> products,

        String uuid
) {

}
