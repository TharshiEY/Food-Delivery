package com.delivery.riderservice.Dto;

import lombok.Data;

@Data
public class RiderDto {
    private long id;
    private String riderId;
    private String name;
    private String contact;
    private String vehicleNumber;
}
