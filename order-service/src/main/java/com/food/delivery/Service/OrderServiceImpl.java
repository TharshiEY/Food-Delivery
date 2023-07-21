package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.Common.GenerateUniqueValue;
import com.food.delivery.Common.KafkaProducer;
import com.food.delivery.Dto.OrderDto;
import com.food.delivery.Entity.Order;
import com.food.delivery.Entity.OrderRepository;
import com.food.delivery.Entity.OrderRequestJson;
import com.food.delivery.Entity.OrderRequestJsonRepository;
import com.food.delivery.Exception.CustomException;
import com.food.delivery.Mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    GenerateUniqueValue uniqueValue;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private final OrderRepository orderRepository;
    private final OrderRequestJsonRepository orderRequestJsonRepository;
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private AtomicInteger orderIdCounter = new AtomicInteger(1);

    @Autowired
    public OrderServiceImpl(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper, OrderRepository orderRepository, OrderRequestJsonRepository orderRequestJsonRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.orderRequestJsonRepository = orderRequestJsonRepository;
    }

    @Override
    public String placeOrder(OrderDto orderDTO) {
        log.info("OrderServiceImpl.placeOrder Invoked");
        try {
            String uniqueOrderId = uniqueValue.generateUniqueOrderId();
            orderDTO.setOrderId(uniqueOrderId);

            //save the order to local db
            log.info("Save order to local db invoked.");
            Order order = orderMapper.mapToOrder(orderDTO);
            orderRepository.save(order);

            // Convert the OrderDto object to JSON string
            String orderJson = objectMapper.writeValueAsString(orderDTO);

            // save the kafka JSON to local db
            OrderRequestJson requestJson = new OrderRequestJson();
            requestJson.setOrderId(orderDTO.getOrderId());
            requestJson.setKafkaQuery(orderJson);
            orderRequestJsonRepository.save(requestJson);

            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage("order-topic", orderJson);

        }catch (JsonProcessingException e){
            // Handle JSON processing exception
            e.printStackTrace();
        }
        return "Waiting for approve the Place Order";
    }

    @Override
    public OrderDto updateOrderStatus(String orderId, String status) {
        try {
            log.info("OrderServiceImpl.updateOrderStatus() invoked.");

            // Update Status in local DB
            orderRepository.updateStatus(status, orderId);
            Order order = orderRepository.findByOrderIdIgnoreCase(orderId);
            OrderDto Dto = orderMapper.mapToOrderDto(order);

            // Convert the OrderDto object to JSON string
            String orderJson = objectMapper.writeValueAsString(Dto);

            // save the kafka JSON to local db
            OrderRequestJson orderRequestJson = orderRequestJsonRepository.findByOrderIdIgnoreCase(orderId);
            if(orderRequestJson != null){
                orderRequestJson.setKafkaQuery(orderJson);
                orderRequestJsonRepository.save(orderRequestJson);
            } else {
                System.out.println("Order with orderId " + orderId + " not found.");
            }

            // Send the JSON string as a Kafka message
            kafkaProducer.sendMessage("status-topic", orderJson);
            return Dto;

        } catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Error occurred while updating status in [OrderService.updateOrderStatus()].");
        }
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
