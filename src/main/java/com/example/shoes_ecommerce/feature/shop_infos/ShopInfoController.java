package com.example.shoes_ecommerce.feature.shop_infos;

import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoRequest;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop_infos")
@AllArgsConstructor
public class ShopInfoController {

    private final ShopInfoService shopInfoService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ShopInfoResponse  createShopInfo(@Valid @RequestBody ShopInfoRequest shopInfoRequest) {
        return  shopInfoService.createShopInfo(shopInfoRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}")
    public ShopInfoResponse updateShopInfosByUuid(
            @PathVariable String uuid,
            @Valid @RequestBody ShopInfoRequest shopInfoRequest) {
        return shopInfoService.updateShopInfosByUuid(uuid, shopInfoRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    public List<ShopInfoResponse> findListShopInfos() {
        return shopInfoService.findListShopInfos();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{uuid}")
    public ShopInfoResponse findByUuid(@PathVariable String uuid) {
        return shopInfoService.findByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShopInfoByUuid(@PathVariable String uuid) {
        shopInfoService.deleteShopInfoByUuid(uuid);
    }

}
