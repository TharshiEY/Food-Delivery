package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.Common.KafkaProducer;
import com.food.delivery.Dto.DeliveryDto;
import com.food.delivery.Entity.Delivery;
import com.food.delivery.Entity.DeliveryRepository;
import com.food.delivery.Mapper.DeliveryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    DeliveryMapper deliveryMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaProducer kafkaProducer;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        log.info("Order Service : DeliveryServiceImpl.createDelivery() Invoked.");
        try {
            Delivery delivery = new Delivery();
            delivery = deliveryRepository.save(deliveryMapper.mapToDelivery(deliveryDto));

            // Convert the OrderDto object to JSON string
            String orderJson = objectMapper.writeValueAsString(deliveryMapper.mapToDeliveryDto(delivery));

            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage("Req-delivery-topic", orderJson);

//            delivery = deliveryRepository.findByIdAndStatusTrue(delivery.getId());
            Delivery d = deliveryRepository.findByOrderIdAndRiderIdAndStatusTrue(delivery.getOrderId(),delivery.getRiderId());
            return deliveryMapper.mapToDeliveryDto(d);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @RabbitListener(queues = "Res-queue-delivery")
    public void consumeResDelivery(String message){
        log.info("Order Service : OrderServiceImpl.consumeResDelivery() Invoked"+ message);
        try {
            DeliveryDto dto = objectMapper.readValue(message, DeliveryDto.class);
            deliveryRepository.updateStatus(Boolean.FALSE, dto.getRiderId(), dto.getOrderId());
            DeliveryDto deliveryDto = deliveryMapper.mapToDeliveryDto(deliveryRepository.findByOrderIdAndRiderId(dto.getRiderId(),dto.getOrderId()));
            log.info("Order Service : OrderServiceImpl.consumeResDelivery() Invoked."+ deliveryDto);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
