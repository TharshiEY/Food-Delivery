package com.food.delivery.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName = AppConfigs.topicName; // Replace with your topic name

    @Autowired
    KafkaConfig kafkaConfig;
    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        //this is use KafkaProducer class
//        kafkaTemplate.send(topicName, message);
        //this is used KafkaConfig file
        kafkaConfig.kafkaTemplate().send(topicName,message);
        System.out.println("Message sent: " + message);
    }
}

