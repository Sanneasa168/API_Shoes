package com.example.shoes_ecommerce.feature.shop_infos;

import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoRequest;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoResponse;

import java.util.List;

public interface ShopInfoService {

    ShopInfoResponse createShopInfo(ShopInfoRequest shopInfoRequest);

    ShopInfoResponse updateShopInfosByUuid(String uuid, ShopInfoRequest shopInfoRequest);

    List<ShopInfoResponse> findListShopInfos();


    ShopInfoResponse findByUuid(String uuid);

    void deleteShopInfoByUuid(String uuid);
}
