package com.example.shoes_ecommerce.mapping;
import com.example.shoes_ecommerce.domain.Orders;
import com.example.shoes_ecommerce.feature.orders.dto.OrderRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderResponse;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateRequest;
import com.example.shoes_ecommerce.feature.orders.dto.OrderUpdateResponse;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapping {

    Orders   toOrders(OrderRequest orderRequest);

    OrderResponse fromOrderResponse(Orders orders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrdersURequest( OrderUpdateRequest orderUpdateRequest, @MappingTarget Orders orders);

    OrderUpdateResponse fromUpdateOrderResponse(Orders orders);


}