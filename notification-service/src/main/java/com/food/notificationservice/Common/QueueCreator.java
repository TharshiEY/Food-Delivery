package com.food.notificationservice.Common;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueCreator {

    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public QueueCreator(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void createQueue(String queueName) {
        rabbitAdmin.declareQueue(new Queue(queueName));
    }
}

