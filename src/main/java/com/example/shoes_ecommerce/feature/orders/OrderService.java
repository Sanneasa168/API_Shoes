package com.example.shoes_ecommerce.feature.orders;


import com.example.shoes_ecommerce.feature.orders.dto.OrderRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderResponse;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateResponse;
import org.springframework.data.domain.Page;

public interface OrderService {


    OrderResponse createOrder(OrderRequest orderRequest);

    Page<OrderResponse> findAllOrders(int pageNumber, int pageSize);

    OrderResponse findByOrderUuid(String uuid);

    OrderUpdateResponse updateOrder(String uuid, OrderUpdateRequest orderUpdateRequest);

    void deleteOrder(String uuid);
}
