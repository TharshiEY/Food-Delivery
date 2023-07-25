package com.delivery.riderservice.Dto;

import lombok.Data;

@Data
public class NotificationDto {
    private String id;
    private String name;
    private String message;
    private Object obj;
}
