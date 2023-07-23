package com.food.restaurantservice.Entity;

import com.food.restaurantservice.Common.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;
}
