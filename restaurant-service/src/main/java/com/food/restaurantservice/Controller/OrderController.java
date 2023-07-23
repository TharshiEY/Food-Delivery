package com.food.restaurantservice.Controller;

import com.food.restaurantservice.Dto.OrderDto;
import com.food.restaurantservice.Dto.ResponseDTO;
import com.food.restaurantservice.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/restaurant/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping()
    public ResponseDTO<OrderDto> orderAcceptance(@RequestParam("orderID") String orderId){
        log.info("OrderController.orderAcceptance() invoked." +orderId);
        String order = orderService.orderAcceptance(orderId);
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setStatus(order);
        if (order != null && orderDto != null) {
            return new ResponseDTO<>(true, "Order found", orderDto);
        } else {
            return new ResponseDTO<>(false, "Order not found or Order is null", null);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseDTO<OrderDto> updateOrderStatus(@PathVariable("orderId") String orderId, @RequestParam("status") String status){
        log.info("OrderController.updateOrderStatus() invoked." +orderId);
        String s = orderService.updateOrderStatus(orderId, status);
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setStatus(s);
        if (s != null && orderDto != null) {
            return new ResponseDTO<>(true, "Order status found", orderDto);
        } else {
            return new ResponseDTO<>(false, "Order status not found", null);
        }
    }

    @PostMapping()
    public ResponseDTO<OrderDto> placeOrder(@RequestBody OrderDto orderDTO){
        log.info("OrderController.placeOrder(OrderDTO orderDTO) Invoked.");
        OrderDto Dto = orderService.placeOrder(orderDTO);
        if (Dto != null) {
            return new ResponseDTO<>(true, "Order found", Dto);
        } else {
            return new ResponseDTO<>(false, "Order not found", null);
        }
    }
}
