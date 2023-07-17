package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.Common.KafkaProducer;
import com.food.delivery.Dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
//    private final KafkaConsumer<Integer, String> kafkaConsumer;
    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    public OrderServiceImpl(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper
//            ,KafkaConsumer<Integer, String> kafkaConsumer
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
//        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public String placeOrder(OrderDTO orderDTO) {
        log.info("OrderServiceImpl.placeOrder Invoked");
        try {
            // Convert the OrderDto object to JSON string
            String orderJson = objectMapper.writeValueAsString(orderDTO);

            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage(orderJson);
//            consumeKafkaMessage();
        }catch (JsonProcessingException e){
            // Handle JSON processing exception
            e.printStackTrace();
        }
        return "Placed Order";
    }

    @KafkaListener(topics = "order-topic")
    public void consumeKafkaMessage(String message) {
        log.info("OrderServiceImpl.consumeKafkaMessage Invoked............{}" +message);
        try{
            OrderDTO dto = objectMapper.readValue(message, OrderDTO.class);
            saveOrder(dto);
        }catch (Exception e){

        }
    }
    public OrderDTO saveOrder(OrderDTO orderDTO){
        log.info("OrderServiceImpl.saveOrder Invoked...........{}" +orderDTO);
        return orderDTO;
    }
}
