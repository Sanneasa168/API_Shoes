package com.example.shoes_ecommerce.feature.Warranties;

import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesRequest;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesResponse;
import java.util.List;

public interface WarrantiesService {

    WarrantiesResponse createWarranties(WarrantiesRequest warrantiesRequest);

    List<WarrantiesResponse>  getAllWarranties();

    void deleteWarrantiesByUuid(String uuid);

}
