package com.food.delivery.Dto;

import lombok.Data;

@Data
public class DeliveryDto {
    private long id;
    private String orderId;
    private String riderId;
    private Boolean status;
}
