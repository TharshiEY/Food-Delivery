package com.food.restaurantservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.restaurantservice.Dto.OrderDto;
import com.food.restaurantservice.Entity.Order;
import com.food.restaurantservice.Entity.OrderRepository;
import com.food.restaurantservice.Exception.CustomException;
import com.food.restaurantservice.Mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;


    private final ObjectMapper objectMapper;

    @Autowired
    public OrderServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String orderAcceptance(String orderId) {
        return null;
    }


    @KafkaListener(topics = "order-topic")
    public OrderDto consumeKafkaMessage(String message) {
        try{
            log.info("OrderServiceImpl.consumeKafkaMessage() Invoked............{}" +message);
            OrderDto dto = objectMapper.readValue(message, OrderDto.class);
            return placeOrder(dto);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Error occurred while consume the order. Please try again later.", e);
        }
    }

    @Override
    public OrderDto placeOrder(OrderDto orderDTO) {
        try {
            log.info("OrderServiceImpl.placeOrder() invoked.");
            Order order = orderMapper.mapToOrder(orderDTO);
            Order saveorder = orderRepository.save(order);
            log.info("OrderServiceImpl.Saved. Successfully!!!");
            return orderMapper.mapToOrderDto(saveorder);
        } catch (Exception e) {
            log.error("Error while placing the order: " + e.getMessage());
            throw new CustomException("Error occurred while placing the order. Please try again later.", e);
        }
    }

    @KafkaListener(topics = "status-topic")
    public String consumeKafkaMessageStatus(String message) {
        try{
            log.info("OrderServiceImpl.consumeKafkaMessageStatus() Invoked............{}" +message);
            OrderDto dto = objectMapper.readValue(message, OrderDto.class);
            return updateOrderStatus(dto.getOrderId(),dto.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Error occurred while consume the order status. Please try again later.", e);
        }
    }

    @Override
    public String updateOrderStatus(String orderId, String status) {
        try {
            log.info("OrderServiceImpl.updateOrderStatus() invoked.");
            orderRepository.updateStatus(status, orderId);
            return orderRepository.findByOrderIdIgnoreCase(orderId).getStatus();
        } catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Error occurred while updating status. ");
        }
    }

    @Override
    public void checkFailureRequest() {
//        orderRepository.findByStatusLikeIgnoreCaseOrderByOrderIdAsc();
    }


}
