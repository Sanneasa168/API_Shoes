package com.example.shoes_ecommerce.feature.products;

import com.example.shoes_ecommerce.feature.products.dto.ProductRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductResponse;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateRequest;
import com.example.shoes_ecommerce.feature.products.dto.ProductUpdateResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse createProduct(
            @Valid
            @RequestBody ProductRequest productRequest) {
        return  productService.createProduct(productRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    Page<ProductResponse> findAllProduct(
            @RequestParam(required = false,defaultValue="0") int pageNumber,
            @RequestParam(required = false,defaultValue = "25") int pageSize
    ){
        return  productService.findListProducts(pageNumber,pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{uuid}")
    ProductResponse findProductByUuid(
            @Valid @PathVariable("uuid") String uuid){
        return productService.findProductByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}/update")
    ProductUpdateResponse updateProduct(@Valid @PathVariable("uuid") String uuid,
                                        @RequestBody ProductUpdateRequest productUpdateRequest) {

        return productService.updateProductByUuid(uuid,productUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/delete")
    void deleteProduct(@Valid @PathVariable("uuid") String uuid) {
        productService.deleteProductByUuid(uuid);
    }

}
