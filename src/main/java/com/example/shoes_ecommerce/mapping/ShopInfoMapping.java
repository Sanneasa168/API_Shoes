package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.ShopInfos;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoRequest;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShopInfoMapping {

      ShopInfos fromShopRequest(ShopInfoRequest shopInfoRequest);

      ShopInfoResponse toShopResponse(ShopInfos shopInfos);

      @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
      void updateRequestShopInfo(ShopInfoRequest shopInfoRequest , @MappingTarget ShopInfos shopInfos);
}
