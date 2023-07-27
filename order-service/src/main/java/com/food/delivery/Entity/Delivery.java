package com.food.delivery.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "rider_id")
    private String riderId;

    @Column(name = "status")
    private Boolean status;
}
