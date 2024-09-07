package com.example.shoes_ecommerce.feature.Warranties;

import com.example.shoes_ecommerce.domain.Warranties;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarrantiesRepository extends JpaRepository<Warranties, Integer> {

     Optional<Warranties>  findByUuid(String uuid);

}
