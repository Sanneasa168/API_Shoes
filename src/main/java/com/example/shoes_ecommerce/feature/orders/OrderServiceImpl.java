package com.example.shoes_ecommerce.feature.orders;

import com.example.shoes_ecommerce.domain.Orders;
import com.example.shoes_ecommerce.domain.Products;
import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.orders.dto.OrderRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderResponse;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateResponse;
import com.example.shoes_ecommerce.feature.products.ProductRepository;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import com.example.shoes_ecommerce.mapping.OrderMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements  OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapping orderMapping;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        // Validate Products
        List<Products> products = orderRequest.productUuid().stream()
                .map(uuid -> productRepository
                        .findByUuid(uuid)
                        .orElseThrow(
                                () -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Product with UUID " + uuid + " has not been found"
                                )
                        ))
                .collect(Collectors.toList());


        // Validate User UUID
        Users users =  usersRepository
                .findByUuid(orderRequest.userUuid())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User with UUID " + orderRequest.userUuid() + " has not been found"
                        )
                );
        // Map DTO to Entity
        Orders orders = orderMapping.toOrders(orderRequest);

        // Set the data
        orders.setIsOrder(false);
        orders.setOrderDate(LocalDateTime.now());
        orders.setProducts(products);
        orders.setUuid(UUID.randomUUID().toString());

        // Save The order
        orders=orderRepository.save(orders);
        return orderMapping.fromOrderResponse(orders);
    }

    @Override
    public Page<OrderResponse> findAllOrders(int pageNumber, int pageSize) {

        // Sort by ID in descending order
        Sort sortBy = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortBy);

        // Find all orders with pagination and sorting
        Page<Orders> orders = orderRepository.findAll(pageRequest);

        // Map the Orders entities to OrderResponse DTOs
        return  orders.map(orderMapping::fromOrderResponse);

    }

    @Override
    public OrderResponse findByOrderUuid(String uuid) {

        Orders orders = orderRepository
                .findByUuid(uuid)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                                ,"Order not been found"
                        )
                );
        return orderMapping.fromOrderResponse(orders);
    }

    @Override
    public OrderUpdateResponse updateOrder(String uuid, OrderUpdateRequest orderUpdateRequest) {

        // Validate Orders UUID 
        Orders orders = orderRepository
                .findByUuid(uuid)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                                ,"Order not been found"
                        )
                );

        // Update the Orders entity with data from the update request
         orderMapping.updateOrdersURequest(orderUpdateRequest,orders);

        // Save the updated order
         orders = orderRepository.save(orders);

        // Return the response DTO
        return orderMapping.fromUpdateOrderResponse(orders);
    }

    @Override
    public void deleteOrder(String uuid) {

        // Find By UUID
        Orders orders = orderRepository
                .findByUuid(uuid)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                                ,"Order not been  found"
                        )
                );

        // Delete Orders
        orderRepository.delete(orders);
    }

}
