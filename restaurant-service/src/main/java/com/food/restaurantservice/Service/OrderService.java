package com.food.restaurantservice.Service;

import com.food.restaurantservice.Dto.OrderDto;

public interface OrderService {

    public String orderAcceptance(Long orderId);

    public OrderDto placeOrder(OrderDto orderDTO);
}
