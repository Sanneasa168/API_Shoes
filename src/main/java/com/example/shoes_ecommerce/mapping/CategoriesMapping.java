package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Categories;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesRequest;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoriesMapping {

    // Transfer date  dto to model
    CategoriesResponse toCategoriesResponse(Categories categories);

    // Transfer date model to dto
    Categories fromCategoriesRequest(CategoriesRequest categoriesRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoriesRequest( CategoriesRequest categoriesRequest, @MappingTarget  Categories categories);
}
