package com.delivery.riderservice.Service;

import com.delivery.riderservice.Common.QueueCreator;
import com.delivery.riderservice.Dto.NotificationDto;
import com.delivery.riderservice.Dto.RiderDto;
import com.delivery.riderservice.Entity.Rider;
import com.delivery.riderservice.Entity.RiderRepository;
import com.delivery.riderservice.Mapper.RiderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    private QueueCreator queueCreator;

    @Autowired
    RiderMapper riderMapper;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter messageConverter = new SimpleMessageConverter();

    public RiderServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "Save_Rider_Queue")
    public void consumeRider(String message){
        log.info("RiderServiceImpl.consumeRider() Invoked"+ message);
        try {
            RiderDto dto = objectMapper.readValue(message, RiderDto.class);
            createRider(dto);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public RiderDto createRider(RiderDto riderDto) {
        log.info("RiderServiceImpl.createRider() Invoked.");
        Rider rider = riderMapper.mapToRider(riderDto);
        rider.setRiderId(riderDto.getRiderId());
        Rider saverider = riderRepository.save(rider);
        RiderDto dto = riderMapper.mapToRiderDto(saverider);

        NotificationDto w = new NotificationDto();
        w.setId("1AW");
        w.setName(riderDto.getName());
        w.setMessage(" welcome to the Deliver service /n you registered successfully !!!!");
        w.setObj(null);
        sendNotification(w);
        return dto;
    }

    public void sendNotification(NotificationDto notificationDto) {
        try {
            // create queue
            queueCreator.createQueue("Rider_Notification");

            // Convert the OrderDto object to JSON string
            String Json = objectMapper.writeValueAsString(notificationDto);
            log.info(String.format("Json message sent -> %s", notificationDto.toString()));
            rabbitTemplate.convertAndSend("Rider_Notification",Json);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

}
