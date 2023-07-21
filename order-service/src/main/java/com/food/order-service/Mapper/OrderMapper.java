package com.food.delivery.Mapper;


import com.food.delivery.Dto.OrderDto;
import com.food.delivery.Entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static Order mapToOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }
        Order order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setName(orderDto.getName());
//        order.setStatus(Status.valueOf(orderDto.getStatus()));
        order.setStatus(orderDto.getStatus());
        return order;
    }

    public static OrderDto mapToOrderDto(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderId(order.getOrderId());
        orderDto.setName(order.getName());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
}
