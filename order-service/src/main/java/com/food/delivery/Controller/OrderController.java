package com.food.delivery.Controller;

import com.food.delivery.Dto.OrderDTO;
import com.food.delivery.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping()
    public String placeOrder(@RequestBody OrderDTO orderDTO){
        log.info("OrderController.placeOrder(OrderDTO orderDTO) Invoked.");
        return orderService.placeOrder(orderDTO);
    }

    @GetMapping("/{orderId}")
    public boolean orderStatus(@PathVariable("orderId") String orderId){
        log.info("OrderController.orderStatus(String orderId) Invoked.");
        return orderService.orderStatus(orderId);
    }
}
