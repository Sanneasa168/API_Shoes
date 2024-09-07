package com.example.shoes_ecommerce.feature.payments;

import com.example.shoes_ecommerce.feature.payments.dto.PaymentCreateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentResponse;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateResponse;
import org.springframework.data.domain.Page;

public interface PaymentService {

    PaymentResponse  createPayment(PaymentCreateRequest paymentCreateRequest);

    Page<PaymentResponse> findAllPayments(int pageNumber, int pageSize);

    PaymentResponse findByUuid(String uuid);

    PaymentUpdateResponse updatePayment(String uuid, PaymentUpdateRequest paymentUpdateRequest);

    void deletePayment(String uuid);


}
