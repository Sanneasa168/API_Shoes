package com.example.shoes_ecommerce.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse(

        String field,

        String detail
) {
}
