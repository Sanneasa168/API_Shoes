package com.example.shoes_ecommerce.feature.Warranties;

import com.example.shoes_ecommerce.domain.Products;
import com.example.shoes_ecommerce.domain.Warranties;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesRequest;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesResponse;
import com.example.shoes_ecommerce.feature.products.ProductRepository;
import com.example.shoes_ecommerce.mapping.WarrantiesMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Service
public class WarrantiesServiceImpl implements WarrantiesService {

    private final WarrantiesRepository warrantiesRepository;
    private final WarrantiesMapping warrantiesMapping;
    private final ProductRepository productRepository;

    @Override
    public WarrantiesResponse createWarranties(WarrantiesRequest warrantiesRequest) {

        // Validate Product
        Products products = productRepository
                .findByUuid(warrantiesRequest.productsUuid())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not been found"
                        )
                );


        Warranties warranties = warrantiesMapping.fromWarrantiesRequest(warrantiesRequest);

        warranties.setUuid(UUID.randomUUID().toString());
        warranties.setWarrantiesDate(LocalDate.of(2025,3,12));
        warranties.setWarrantiesTime(LocalTime.of(23,59,59));
        warranties.setProducts(products);

        warranties = warrantiesRepository.save(warranties);

        return warrantiesMapping.toWarrantiesResponse(warranties);
    }

    @Override
    public List<WarrantiesResponse> getAllWarranties() {

        Sort   sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Warranties> warranties = warrantiesRepository.findAll(sortById);
        return  warrantiesMapping.toWarrantiesResponseList(warranties);

    }

    @Override
    public void deleteWarrantiesByUuid(String uuid) {

        Warranties warranties = warrantiesRepository
                .findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Warranties Uuid is not been found"
                        )
                );
        warrantiesRepository.delete(warranties);
    }

}
