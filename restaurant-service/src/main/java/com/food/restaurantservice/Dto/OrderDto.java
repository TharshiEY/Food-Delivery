package com.food.restaurantservice.Dto;

import com.food.restaurantservice.Common.Status;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String orderId;
    private String name;
    private String status;
}
