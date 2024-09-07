package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Products;
import com.example.shoes_ecommerce.feature.products.dto.ProductRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapping {

    ProductResponse toProductResponse(Products products);

    ProductUpdateResponse toProductUpdateResponse(Products products);

    Products fromProductRequest(ProductRequest productRequest);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateProductRequest(ProductUpdateRequest productUpdateRequest, @MappingTarget Products products);

}
