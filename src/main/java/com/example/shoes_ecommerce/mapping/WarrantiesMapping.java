package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Warranties;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesRequest;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WarrantiesMapping {

    WarrantiesResponse toWarrantiesResponse(Warranties warranties);

    Warranties fromWarrantiesRequest(WarrantiesRequest warrantiesRequest);

    List<WarrantiesResponse> toWarrantiesResponseList(List<Warranties> warranties);

}
