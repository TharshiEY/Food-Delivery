package com.food.restaurantservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.restaurantservice.Common.KafkaProducer;
import com.food.restaurantservice.Dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaProducer kafkaProducer;

    public void sendNotificationKafka(NotificationDto notificationDto){
        log.info("Restaurant Service : NotificationService.sendNotificationKafka() Invoked.");
        try{
            String orderJson = objectMapper.writeValueAsString(notificationDto);
            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage("restaurant-Notification", orderJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
