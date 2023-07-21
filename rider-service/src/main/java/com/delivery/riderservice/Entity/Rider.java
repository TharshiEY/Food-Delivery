package com.delivery.riderservice.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "riders")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rider_id")
    private String riderId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contact;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

}
