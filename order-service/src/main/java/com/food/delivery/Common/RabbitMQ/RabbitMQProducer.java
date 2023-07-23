package com.food.delivery.Common.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String queue, Object message) {
        try {

            // Convert the OrderDto object to JSON string
            String riderJson = objectMapper.writeValueAsString(message);
            log.info(String.format("Json message sent -> %s", message.toString()));

            rabbitTemplate.convertAndSend(queue, riderJson);
            System.out.println("Message sent to RabbitMQ: " + riderJson);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
