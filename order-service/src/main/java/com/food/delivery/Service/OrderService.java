package com.food.delivery.Service;

import com.food.delivery.Dto.OrderDto;

public interface OrderService {
    public String placeOrder(OrderDto orderDTO);

    public boolean orderStatus(String orderId);

    public OrderDto updateOrderStatus(String orderId, String status);
}
