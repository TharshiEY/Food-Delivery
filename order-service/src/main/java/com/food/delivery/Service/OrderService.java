package com.food.delivery.Service;

import com.food.delivery.Dto.OrderDTO;

public interface OrderService {
    public String placeOrder(OrderDTO orderDTO);

    public boolean orderStatus(String orderId);
}
