package com.example.shoes_ecommerce.feature.payments.dto;

import java.time.LocalDateTime;

public record PaymentResponse(

        String uuid,

        double amount,

        String paymentStatus,

        LocalDateTime paymentDate
) {

}
