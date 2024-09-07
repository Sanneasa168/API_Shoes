package com.example.shoes_ecommerce.feature.payments;

import com.example.shoes_ecommerce.domain.Orders;
import com.example.shoes_ecommerce.domain.Payments;
import com.example.shoes_ecommerce.feature.orders.OrderRepository;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentCreateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentResponse;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateRequest;
import com.example.shoes_ecommerce.feature.payments.dto.PaymentUpdateResponse;
import com.example.shoes_ecommerce.mapping.PaymentMapping;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService{

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapping paymentMapping;


    @Override
    public PaymentResponse createPayment(PaymentCreateRequest paymentCreateRequest) {

        // Validate Orders UUID
        Orders orders = orderRepository
                .findByUuid(paymentCreateRequest.orderUuid())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order UUID has not been found "
                        )
                );

        // Map DTO to Domain Model
        Payments payments = paymentMapping.fromPaymentCreateRequest(paymentCreateRequest);

        // Set Initial Payment Data
        payments.setAmount(paymentCreateRequest.amount());
        payments.setPaymentDate(LocalDateTime.now());
        payments.setOrder(orders);
        payments.setUuid(UUID.randomUUID().toString());

        // Simulate Payment Gateway Interaction
        String  paymentStatus = processPaymentWithGateway(paymentCreateRequest.amount());

        // Set Payment Status
        payments.setPaymentStatus(paymentStatus);

        // Save Payment
        paymentRepository.save(payments);

        // Return Payment Response
        return  paymentMapping.toPaymentResponse(payments);

    }

    private String processPaymentWithGateway(double amount) {
        // Simulate interaction with a payment gateway
        // In a real application, you would make an API call to the payment gateway here
        boolean paymentSuccessful = simulatePaymentGatewayInteraction(amount);

        // Return appropriate status based on payment success
        return paymentSuccessful ? "COMPLETED" : "FAILED";
    }

    private boolean simulatePaymentGatewayInteraction(double amount) {
        // Simulate payment processing logic
        // This should be replaced with actual API call to payment gateway
        return amount > 0; // Example condition: assume payment is successful for positive amounts
    }

    @Override
    public Page<PaymentResponse> findAllPayments(int pageNumber, int pageSize) {

        // Create a PageRequest with sorting by ID
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        // Retrieve payments from the repository
        Page<Payments> paymentsPage = paymentRepository.findAll(pageRequest);

        // Map entities to DTOs and return
        return paymentsPage.map(paymentMapping::toPaymentResponse);
    }

    @Override
    public PaymentResponse findByUuid(String uuid) {
        Payments payment = paymentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Payment with UUID " + uuid + " not found"
                ));

        return paymentMapping.toPaymentResponse(payment);
    }

    @Override
    public PaymentUpdateResponse updatePayment(String uuid, PaymentUpdateRequest paymentUpdateRequest) {

        // Find the payment by UUID
        Payments payment = paymentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Payment with UUID " + uuid + " not found"
                ));
        // Update payment with new details
        paymentMapping.updatePaymentFromRequest(paymentUpdateRequest, payment);

        // Save updated payment
        Payments updatedPayment = paymentRepository.save(payment);

        // Return updated payment details
        return paymentMapping.toPaymentUpdateResponse(updatedPayment);
    }

    @Override
    public void deletePayment(String uuid) {

        // Find the payment by UUID
        Payments payment = paymentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Payment with UUID " + uuid + " not found"
                ));

        // Delete payment
        paymentRepository.delete(payment);

    }
}
