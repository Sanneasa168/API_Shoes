package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Payments;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentCreateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentResponse;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapping {

    PaymentResponse  toPaymentResponse(Payments payments);

    Payments   fromPaymentCreateRequest(PaymentCreateRequest paymentCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentFromRequest(PaymentUpdateRequest paymentUpdateRequest, @MappingTarget Payments payments);

    PaymentUpdateResponse toPaymentUpdateResponse(Payments payments);
}
