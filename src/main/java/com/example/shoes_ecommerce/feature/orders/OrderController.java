package com.example.shoes_ecommerce.feature.orders;

import com.example.shoes_ecommerce.feature.orders.dto.OrderRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderResponse;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    Page<OrderResponse> getAllOrders(
            @RequestParam(required = false,defaultValue = "0") int pageNumber,
            @RequestParam(required = false,defaultValue = "25") int pageSize) {

            return orderService.findAllOrders(pageNumber, pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{uuid}")
    OrderResponse findOrderByUuid(@Valid @PathVariable("uuid") String uuid) {

        return  orderService.findByOrderUuid(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}")
    OrderUpdateResponse updateOrder(@Valid @PathVariable("uuid") String uuid, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        return  orderService.updateOrder(uuid,orderUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteOrder(@Valid @PathVariable("uuid") String uuid) {
        orderService.deleteOrder(uuid);
    }

}
