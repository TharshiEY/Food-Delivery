package com.food.notificationservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.notificationservice.Dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SentMailServiceImpl implements SentNotificationService {

    private final ObjectMapper objectMapper;

    public SentMailServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "Rider_Notification")
    public void receiveRabbitMQNotification(String message){
        log.info("Notification Service() : SentMailServiceImpl.receiveRabbitMQNotification() Invoked"+ message);
        try {
            NotificationDto dto = objectMapper.readValue(message, NotificationDto.class);
            sentMail(dto);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "restaurant-Notification")
    public void receiveKafkaNotification(String message) {
        try {
            log.info("Notification Service() : SentMailServiceImpl.receiveKafkaNotification() Invoked............{}" + message);
            NotificationDto dto = objectMapper.readValue(message, NotificationDto.class);
            sentMail(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sentMail(NotificationDto notificationDto) {
        log.info("Notification Service() : SentMailServiceImpl.sentMail() Invoked.");
        System.out.println("Hi" + notificationDto.getName() + " Mail sent successfully !!!!!!!!!! /n" + notificationDto.getMessage());
    }
}
