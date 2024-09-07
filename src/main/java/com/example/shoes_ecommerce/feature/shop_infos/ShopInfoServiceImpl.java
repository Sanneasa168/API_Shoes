package com.example.shoes_ecommerce.feature.shop_infos;

import com.example.shoes_ecommerce.domain.ShopInfos;
import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoRequest;
import com.example.shoes_ecommerce.feature.shop_infos.dto.ShopInfoResponse;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import com.example.shoes_ecommerce.mapping.ShopInfoMapping;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShopInfoServiceImpl implements ShopInfoService {

    private final ShopInfoRepository shopInfoRepository;
    private final ShopInfoMapping shopInfoMapping;

    @Override
    public ShopInfoResponse createShopInfo(ShopInfoRequest shopInfoRequest) {

        // Validate email
        if (shopInfoRepository.existsByEmail(shopInfoRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (shopInfoRepository.existsByPhone(shopInfoRequest.phone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        ShopInfos shop = shopInfoMapping.fromShopRequest(shopInfoRequest);

        // Log to confirm mapping
        System.out.println("Mapped Shop Address: " + shop.getAddress());

        shop.setUuid(UUID.randomUUID().toString());
        shop = shopInfoRepository.save(shop);

        return shopInfoMapping.toShopResponse(shop);
    }

    @Override
    public ShopInfoResponse updateShopInfosByUuid(String uuid, ShopInfoRequest shopInfoRequest) {

        // Fetch the existing shop information by UUID
        ShopInfos existingShopInfo = shopInfoRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop info not found"));

        // Check for email conflict if the email is being updated
        if (!existingShopInfo.getEmail().equals(shopInfoRequest.email()) &&
                shopInfoRepository.existsByEmail(shopInfoRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Check for phone conflict if the phone number is being updated
        if (!existingShopInfo.getPhone().equals(shopInfoRequest.phone()) &&
                shopInfoRepository.existsByPhone(shopInfoRequest.phone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        // Use the mapper to update the entity
        shopInfoMapping.updateRequestShopInfo(shopInfoRequest, existingShopInfo);

        // Save the updated shop information
        ShopInfos updatedShopInfo = shopInfoRepository.save(existingShopInfo);

        // Return the updated response
        return shopInfoMapping.toShopResponse(updatedShopInfo);


    }

    @Override
    public List<ShopInfoResponse> findListShopInfos() {
        // Fetch all ShopInfos from the repository
        List<ShopInfos> shopInfosList = shopInfoRepository.findAll();

        // Map the list of ShopInfos entities to a list of ShopInfoResponse DTOs
        return shopInfosList.stream()
                .map(shopInfoMapping::toShopResponse)
                .collect(Collectors.toList());

    }

    @Override
    public ShopInfoResponse findByUuid(String uuid) {

        // Fetch the ShopInfos entity by UUID from the repository
        ShopInfos shopInfos = shopInfoRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop info not found"));

        // Map the ShopInfos entity to a ShopInfoResponse DTO
        return shopInfoMapping.toShopResponse(shopInfos);

    }

    @Override
    public void deleteShopInfoByUuid(String uuid) {

        // Fetch the ShopInfos entity by UUID from the repository
        ShopInfos shopInfos = shopInfoRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop info not found"));

        // Delete the shop info
        shopInfoRepository.delete(shopInfos);
    }

}
