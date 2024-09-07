package com.example.shoes_ecommerce.feature.categories;

import com.example.shoes_ecommerce.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {

        Boolean existsByName(String name);

        Optional<Categories> findByUuid(String uuid);


}
