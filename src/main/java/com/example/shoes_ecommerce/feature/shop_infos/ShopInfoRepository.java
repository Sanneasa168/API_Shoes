package com.example.shoes_ecommerce.feature.shop_infos;

import com.example.shoes_ecommerce.domain.ShopInfos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopInfoRepository extends JpaRepository<ShopInfos,Long> {

     Optional<ShopInfos> findByUuid(String uuid);

     Optional<ShopInfos> findByEmail(String email);

     boolean existsByEmail(String email);

     boolean existsByPhone(String phone);
}
