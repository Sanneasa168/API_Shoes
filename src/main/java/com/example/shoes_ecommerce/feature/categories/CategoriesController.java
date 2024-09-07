package com.example.shoes_ecommerce.feature.categories;

import com.example.shoes_ecommerce.feature.categories.dto.CategoriesRequest;
import com.example.shoes_ecommerce.feature.categories.dto.CategoriesResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CategoriesResponse createCategories(@Valid @RequestBody CategoriesRequest categoriesRequest){
        return categoriesService.createCategories(categoriesRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    Page<CategoriesResponse> getAllCategories(
            @RequestParam(required = false,defaultValue = "0") int pageNumber,
            @RequestParam(required = false,defaultValue = "25") int pageSize
    ) {
       return  categoriesService.findList(pageNumber, pageSize);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    CategoriesResponse findCategoriesUuid(@Valid @PathVariable("uuid") String uuid){
        return categoriesService.findByUuid(uuid);
    }

    @PutMapping("/{uuid}/updateByUuid")
    @ResponseStatus(HttpStatus.CREATED)
    CategoriesResponse updateCategoriesUuid(@Valid @PathVariable("uuid") String uuid, @RequestBody CategoriesRequest categoriesRequest
    ){
        return categoriesService.updateByUuid(uuid, categoriesRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/delete")
    void deleteCategoriesByUuid(@Valid @PathVariable("uuid") String uuid){
        categoriesService.deleteByUuid(uuid);
    }

}
