//package com.food.delivery.Common.RabbitMQ;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class RabbitMQQueues {
//
//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    @Bean
//    public void createQueues(List<String> queueNames) {
//        for (String queueName : queueNames) {
//            Queue queue = new Queue(queueName);
//
//            rabbitAdmin.declareQueue(queue);
//        }
//    }
//
//    @Bean
//    public void createQueue(String queue){
//        Queue q = new Queue(queue);
//        rabbitAdmin.declareQueue(q);
//    }
//}
