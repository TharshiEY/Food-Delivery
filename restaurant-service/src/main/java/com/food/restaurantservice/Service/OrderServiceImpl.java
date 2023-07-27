package com.food.restaurantservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.restaurantservice.Common.QueueCreator;
import com.food.restaurantservice.Dto.NotificationDto;
import com.food.restaurantservice.Dto.OrderDto;
import com.food.restaurantservice.Entity.Order;
import com.food.restaurantservice.Entity.OrderRepository;
import com.food.restaurantservice.Exception.CustomException;
import com.food.restaurantservice.Mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    NotificationService notificationService;

    @Autowired
    QueueCreator queueCreator;


    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderServiceImpl(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public String orderAcceptance(String orderId) {
        return null;
    }


    @KafkaListener(topics = "Req-order-topic")
    public OrderDto consumeKafkaMessage(String message) {
        try{
            log.info("Restaurant Service : OrderServiceImpl.consumeKafkaMessage() Invoked............{}" +message);
            OrderDto dto = objectMapper.readValue(message, OrderDto.class);
            return placeOrder(dto);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Restaurant Service : Error occurred while consume the order. Please try again later.", e);
        }
    }

    @Override
    public OrderDto placeOrder(OrderDto orderDTO) {
        try {
            log.info("Restaurant Service : OrderServiceImpl.placeOrder() invoked.");
            Order order = orderMapper.mapToOrder(orderDTO);
            Order saveorder = orderRepository.save(order);
            log.info("Restaurant Service : OrderServiceImpl.Saved. Successfully!!!");

            NotificationDto dto = new NotificationDto();
            dto.setId("ORD001");
            dto.setName(orderDTO.getName());
            dto.setMessage(" You got the new order check the app and Accept the order");
            notificationService.sendNotificationKafka(dto);

            // to accept or decline the order logic
            int i = (int) saveorder.getId();
            if (i % 2 == 0){
                updateOrderStatus(orderDTO.getOrderId(),"ACCEPTED");
                String message = objectMapper.writeValueAsString(orderMapper.mapToOrderDto(orderRepository.findByOrderIdIgnoreCase(orderDTO.getOrderId())));
                sendResRabbitMq(message);
            } else {
                updateOrderStatus(orderDTO.getOrderId(),"DENIED");
                String message = objectMapper.writeValueAsString(orderMapper.mapToOrderDto(orderRepository.findByOrderIdIgnoreCase(orderDTO.getOrderId())));
                sendResRabbitMq(message);
            }
            return orderMapper.mapToOrderDto(saveorder);
        } catch (Exception e) {
            log.error("Restaurant Service : Error while placing the order: " + e.getMessage());
            throw new CustomException("Restaurant Service : Error occurred while placing the order. Please try again later.", e);
        }
    }

    @KafkaListener(topics = "status-topic")
    public String consumeKafkaMessageStatus(String message) {
        try{
            log.info("Restaurant Service : OrderServiceImpl.consumeKafkaMessageStatus() Invoked............{}" +message);
            OrderDto dto = objectMapper.readValue(message, OrderDto.class);
            return updateOrderStatus(dto.getOrderId(),dto.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Restaurant Service : Error occurred while consume the order status. Please try again later.", e);
        }
    }

    @Override
    public String updateOrderStatus(String orderId, String status) {
        try {
            log.info("Restaurant Service : OrderServiceImpl.updateOrderStatus() invoked.");
            orderRepository.updateStatus(status, orderId);
            return orderRepository.findByOrderIdIgnoreCase(orderId).getStatus();
        } catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Restaurant Service : Error occurred while updating status. ");
        }
    }

    public void sendResRabbitMq(String message){
        log.info("Restaurant Service : OrderServiceImpl.sendResRabbitMq() Invoked");
        // create queue
        queueCreator.createQueue("Res-queue-order");
        rabbitTemplate.convertAndSend("Res-queue-order", message);
    }

    @Override
    public void checkFailureRequest() {
//        orderRepository.findByStatusLikeIgnoreCaseOrderByOrderIdAsc();
    }


}
