package com.example.shoes_ecommerce.feature.payments;

import com.example.shoes_ecommerce.feature.payments.dto.PaymentCreateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentResponse;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PaymentResponse createPayment(@Valid @RequestBody PaymentCreateRequest paymentCreateRequest){
        return  paymentService.createPayment(paymentCreateRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/findAll")
    Page<PaymentResponse> getPayments(
            @Valid  @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return  paymentService.findAllPayments(page,size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{uuid}")
    PaymentResponse findPaymentByUuid(@Valid @PathVariable("uuid") String uuid){
        return  paymentService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}")
    PaymentUpdateResponse  updatePaymentByUuid(@Valid @PathVariable("uuid") String uuid ,@RequestBody PaymentUpdateRequest paymentUpdateRequest){
        return paymentService.updatePayment(uuid,paymentUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deletePaymentByUuid(@Valid @PathVariable("uuid") String uuid){
        paymentService.deletePayment(uuid);
    }

}
