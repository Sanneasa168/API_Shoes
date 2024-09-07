package com.example.shoes_ecommerce.feature.products;

import com.example.shoes_ecommerce.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products,Integer> {

      Optional<Products> findByUuid(String uuid);

      boolean existsByUuid(String uuid);


}
