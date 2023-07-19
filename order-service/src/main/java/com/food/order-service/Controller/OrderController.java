package com.food.delivery.Controller;

import com.food.delivery.Dto.OrderDTO;
import com.food.delivery.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping()
    public String placeOrder(@RequestBody OrderDTO orderDTO){
        log.info("OrderController.placeOrder Invoked");
        return orderService.placeOrder(orderDTO);
    }
}
