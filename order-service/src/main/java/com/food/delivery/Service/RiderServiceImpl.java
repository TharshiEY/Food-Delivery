package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.Common.GenerateUniqueValue;
import com.food.delivery.Common.RabbitMQ.RabbitMQProducer;
import com.food.delivery.Dto.RiderDto;
import com.food.delivery.Entity.Rider;
import com.food.delivery.Entity.RiderRepository;
import com.food.delivery.Mapper.RiderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderMapper riderMapper;

    @Autowired
    GenerateUniqueValue generateUniqueValue;

    @Autowired
    RabbitAdmin rabbitAdmin;

    private final RabbitMQProducer rabbitMQProducer;
    private final ObjectMapper objectMapper;
    private static final String QUEUE_NAME = "Save_Rider_Queue";

    public RiderServiceImpl(RabbitMQProducer rabbitMQProducer, ObjectMapper objectMapper) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.objectMapper = objectMapper;
    }

    @Override
    public RiderDto createRider(RiderDto riderDto) throws JsonProcessingException {
        log.info("RiderServiceImpl.createRider() Invoked.");

        Rider rider = riderMapper.mapToRider(riderDto);
        rider.setRiderId(generateUniqueValue.generateUniqueOrderId());

        // save Rider in local DB
        Rider saverider = riderRepository.save(rider);
        RiderDto dto = riderMapper.mapToRiderDto(saverider);

        // Create queue
        Queue q = new Queue("queue-1");
        rabbitAdmin.declareQueue(q);


        // RabbitMQ Sending a message
        rabbitMQProducer.sendMessage("queue-1",dto);
        return dto;
    }

    @Override
    public String testRabbitMQ(RiderDto riderDto) {
        rabbitTemplate.convertAndSend("rabbit_queue",riderDto);
        log.info("successful rabbitMQ........");
        return "successful rabbitMQ........";

    }
}
