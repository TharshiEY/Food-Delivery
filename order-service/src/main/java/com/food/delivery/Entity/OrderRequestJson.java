package com.food.delivery.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_rqst_json")
public class OrderRequestJson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "kafka_query")
    private String kafkaQuery;
}
