package com.example.shoes_ecommerce.feature.fileupload;

import lombok.Builder;

@Builder
public record FileUploadResponse(

        String name,

        String uri,

        String contentType,

        Long  size
) {
}
