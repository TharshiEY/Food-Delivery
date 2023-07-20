package com.food.restaurantservice.Controller;

import com.food.restaurantservice.Dto.OrderDto;
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
    public String orderAcceptance(@RequestParam("orderID") Long orderId){
        log.info("OrderController.orderAcceptance() invoked." +orderId);
        //"order accepted or rejected."
        return orderService.orderAcceptance(orderId);
    }

    @PostMapping()
    public OrderDto placeOrder(@RequestBody OrderDto orderDTO){
        log.info("OrderController.placeOrder(OrderDTO orderDTO) Invoked.");
        return orderService.placeOrder(orderDTO);
    }
}
