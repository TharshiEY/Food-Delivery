package com.delivery.riderservice.Service;

import com.delivery.riderservice.Common.QueueCreator;
import com.delivery.riderservice.Dto.DeliveryDto;
import com.delivery.riderservice.Entity.DeliveryRepository;
import com.delivery.riderservice.Exception.CustomException;
import com.delivery.riderservice.Mapper.DeliveryMapper;
import com.delivery.riderservice.Mapper.RiderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    DeliveryMapper deliveryMapper;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    QueueCreator queueCreator;

    @Autowired
    RiderMapper riderMapper;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public DeliveryServiceImpl(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }


    @KafkaListener(topics = "Req-delivery-topic")
    public DeliveryDto consumeKafkaMessage(String message) {
        try{
            log.info("Rider Service : DeliveryServiceImpl.consumeKafkaMessage() Invoked............{}" +message);
            DeliveryDto dto = objectMapper.readValue(message, DeliveryDto.class);
            return createDelivery(dto);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Rider Service : Error occurred while consume the order. Please try again later.", e);
        }
    }

    public DeliveryDto createDelivery(DeliveryDto deliveryDto){
        log.info("Rider Service : DeliveryServiceImpl.createDelivery() Invoked.");
        DeliveryDto dto = new DeliveryDto();
        try {
            dto = deliveryMapper.mapToDeliveryDto(deliveryRepository.save(deliveryMapper.mapToDelivery(deliveryDto)));
            sendResRabbitMq(objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    public void sendResRabbitMq(String message){
        log.info("Restaurant Service : OrderServiceImpl.sendResRabbitMq() Invoked");
        // create queue
        queueCreator.createQueue("Res-queue-delivery");
        rabbitTemplate.convertAndSend("Res-queue-delivery", message);
    }
}
