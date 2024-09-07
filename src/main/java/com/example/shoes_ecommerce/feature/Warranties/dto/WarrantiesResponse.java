package com.example.shoes_ecommerce.feature.Warranties.dto;

import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public record WarrantiesResponse(

        LocalTime warrantiesTime,

        LocalDate warrantiesDate,

        String uuid,

        ProductResponse products

) {
}
