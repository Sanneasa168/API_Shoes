package com.example.shoes_ecommerce.feature.products;

import com.example.shoes_ecommerce.domain.Categories;
import com.example.shoes_ecommerce.domain.Products;
import com.example.shoes_ecommerce.feature.categories.CategoryRepository;
import com.example.shoes_ecommerce.feature.products.dto.ProductRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateResponse;
import com.example.shoes_ecommerce.mapping.ProductMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Service
public class ProductServiceImpl  implements  ProductService{

    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductMapping productMapping;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        // Validate Category UUID
        Categories categories = categoryRepository
                .findByUuid(productRequest.categoriesUuid())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not been found"
                ));

        // Map DTO to Domain Model
        Products products = productMapping.fromProductRequest(productRequest);

        // Set data
        products.setStatus(true);
        products.setUuid(UUID.randomUUID().toString());
        products.setCategories(categories);

        // Save Product Data
        productRepository.save(products);

        // Map to Response DTO and return
        return productMapping.toProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findListProducts(int pageNumber, int pageSize) {

        // Sort by ID
        Sort sortById = Sort.by(Sort.Direction.DESC,"id");
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sortById);
        Page<Products> products = productRepository.findAll(pageRequest);
        return  products.map(productMapping::toProductResponse);

    }

    @Override
    public ProductResponse findProductByUuid(String uuid) {

        // Validate UUID
        Products products = productRepository
                .findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product Uuid  not been found"
                        )
                );

        productRepository.save(products);
        return  productMapping.toProductResponse(products);
    }

    @Override
    public ProductUpdateResponse updateProductByUuid(String uuid, ProductUpdateRequest productUpdateRequest) {

        // Validate UUID
        Products products = productRepository
                .findByUuid(uuid)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product Uuid not been found"
                        )
                );

        // Map update request to the existing product entity
        productMapping.toUpdateProductRequest(productUpdateRequest,products);

        // Save the updated product entity
        productRepository.save(products);

        // Convert to response DTO and return
        return  productMapping.toProductUpdateResponse(products);
    }

    @Override
    public void deleteProductByUuid(String uuid) {

        // Validate UUID
        Products products = productRepository
                .findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product Uuid  not been found"
                        )
                );

        // Delete product
        productRepository.delete(products);

    }
}
