package com.example.shoes_ecommerce.feature.categories;

import com.example.shoes_ecommerce.feature.categories.dto.CategoriesRequest;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesResponse;
import org.springframework.data.domain.Page;


public interface CategoriesService {

    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);

    Page<CategoriesResponse> findList(int pageNumber, int pageSize);

    CategoriesResponse findByUuid(String uuid);

    CategoriesResponse updateByUuid(String uuid,CategoriesRequest categoriesRequest);

    void deleteByUuid(String uuid);

}
