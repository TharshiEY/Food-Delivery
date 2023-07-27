//package com.food.restaurantservice.Common;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMqToKafkaConnector {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public RabbitMqToKafkaConnector(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @RabbitListener(queues = "rabbitmq-queue")
//    public void receiveMessageFromRabbitMQ(Message rabbitMessage) {
//        String messagePayload = new String(rabbitMessage.getBody());
//        kafkaTemplate.send("kafka-topic", messagePayload);
//    }
//}
//
