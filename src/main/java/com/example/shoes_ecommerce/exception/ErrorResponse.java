package com.example.shoes_ecommerce.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse<T> {

    private Integer code;

    private T reason;
}
