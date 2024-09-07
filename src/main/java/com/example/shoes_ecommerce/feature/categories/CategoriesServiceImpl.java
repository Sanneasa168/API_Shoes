package com.example.shoes_ecommerce.feature.categories;
import com.example.shoes_ecommerce.domain.Categories;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesRequest;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesResponse;
import com.example.shoes_ecommerce.mapping.CategoriesMapping;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesMapping categoriesMapping;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {

        // Validate name
        if(categoryRepository.existsByName(categoriesRequest.name())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Categories already exists"
            );
        }

        Categories  categories= categoriesMapping.fromCategoriesRequest(categoriesRequest);
        categories.setUuid(UUID.randomUUID().toString());

        categoryRepository.save(categories);
        return categoriesMapping.toCategoriesResponse(categories);
    }

    @Override
    public Page<CategoriesResponse> findList(int pageNumber, int pageSize) {

        Sort sortById = Sort.by(Sort.Direction.DESC,"id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);
        Page<Categories> categories = categoryRepository.findAll(pageRequest);
        return categories.map(categoriesMapping::toCategoriesResponse);
    }

    @Override
    public CategoriesResponse findByUuid(String uuid) {

        // Validate UUID
        Categories categories = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        categoryRepository.save(categories);
        return  categoriesMapping.toCategoriesResponse(categories);
    }

    @Override
    public CategoriesResponse updateByUuid(String uuid, CategoriesRequest categoriesRequest) {

        // Validate UUID
        Categories categories = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        categoriesMapping.updateCategoriesRequest(categoriesRequest,categories);
        categoryRepository.save(categories);
        return  categoriesMapping.toCategoriesResponse(categories);
    }

    @Override
    public void deleteByUuid(String uuid) {

        // Validate UUID
        Categories categories = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        categoryRepository.delete(categories);
    }

}
