package com.delivery.riderservice.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RiderDto implements Serializable {
    private long id;
    private String riderId;
    private String name;
    private String contact;
    private String vehicleNumber;
}
