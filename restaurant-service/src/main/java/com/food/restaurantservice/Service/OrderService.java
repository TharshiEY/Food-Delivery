package com.food.restaurantservice.Service;

import com.food.restaurantservice.Dto.OrderDto;

public interface OrderService {

    public String orderAcceptance(String orderId);

    public OrderDto placeOrder(OrderDto orderDTO);

    public String updateOrderStatus(String orderId, String status);

    public void checkFailureRequest();
}
