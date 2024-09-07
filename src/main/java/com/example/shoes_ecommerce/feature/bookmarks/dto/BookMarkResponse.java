package com.example.shoes_ecommerce.feature.bookmarks.dto;

import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;

public record BookMarkResponse(

        ProductResponse products ,

        Boolean isBookmark,

        String  uuid

) {
}
