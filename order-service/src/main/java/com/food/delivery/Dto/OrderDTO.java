package com.food.delivery.Dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String orderId;
    private String name;
    private String status;

}
