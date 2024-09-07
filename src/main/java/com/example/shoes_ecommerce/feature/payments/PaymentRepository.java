package com.example.shoes_ecommerce.feature.payments;

import com.example.shoes_ecommerce.domain.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments,Long> {

    Optional<Payments> findByUuid(String uuid);
}
