package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.Common.GenerateUniqueValue;
import com.food.delivery.Common.KafkaProducer;
import com.food.delivery.Dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

//    private final KafkaConsumer<Integer, String> kafkaConsumer;
    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    GenerateUniqueValue uniqueValue;

    @Autowired
    public OrderServiceImpl(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private AtomicInteger orderIdCounter = new AtomicInteger(1);

    @Override
    public String placeOrder(OrderDTO orderDTO) {
        log.info("OrderServiceImpl.placeOrder Invoked");
        try {
            String uniqueOrderId = uniqueValue.generateUniqueOrderId();
            orderDTO.setOrderId(uniqueOrderId);

            // Convert the OrderDto object to JSON string
            String orderJson = objectMapper.writeValueAsString(orderDTO);

            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage(orderJson);

        }catch (JsonProcessingException e){
            // Handle JSON processing exception
            e.printStackTrace();
        }
        return "Waiting for approve the Place Order";
    }

    @Override
    public boolean orderStatus(String orderId) {
        return false;
    }

//    @KafkaListener(topics = "order-topic")
//    public OrderDTO consumeKafkaMessage(String message) {
//        try{
//            log.info("OrderServiceImpl.consumeKafkaMessage Invoked............{}" +message);
//            OrderDTO dto = objectMapper.readValue(message, OrderDTO.class);
//            return saveOrder(dto);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new CustomException("Error occurred while consume the order. Please try again later.", e);
//        }
//    }
//    public OrderDTO saveOrder(OrderDTO orderDTO){
//        log.info("OrderServiceImpl.saveOrder Invoked...........{}" +orderDTO);
//        return orderDTO;
//    }
}
