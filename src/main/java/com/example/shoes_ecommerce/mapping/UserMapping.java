package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.auth.dto.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapping {

    Users fromRegisterRequest(RegisterRequest registerRequest);

}
