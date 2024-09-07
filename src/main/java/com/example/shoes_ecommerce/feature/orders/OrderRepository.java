package com.example.shoes_ecommerce.feature.orders;


import com.example.shoes_ecommerce.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Optional<Orders> findByUuid(String uuid);

}
