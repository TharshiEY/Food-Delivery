package com.food.restaurantservice.Common;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    private static final String QUEUE_NAME = "Save_Rider_Queue";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, true);
//    }

//    @Bean
//    public Queue ResQueueOrder(){return new Queue("Res-queue-order", true);}

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey("Res-queue-order");
        return rabbitTemplate;
    }
}