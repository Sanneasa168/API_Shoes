package com.example.shoes_ecommerce.feature.products;


import com.example.shoes_ecommerce.feature.products.dto.ProductRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    Page<ProductResponse>  findListProducts(int pageNumber, int  pageSize);

    ProductResponse findProductByUuid(String uuid);

    ProductUpdateResponse updateProductByUuid(String uuid, ProductUpdateRequest productUpdateRequest);

    void deleteProductByUuid(String uuid);

}
