package com.food.restaurantservice.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/restaurant/order")
public class OrderController {

    @GetMapping()
    public String orderAcceptance(@RequestParam("orderID") Long orderId){
        log.info("OrderController.orderAcceptance() invoked." +orderId);
        return "order accepted or rejected.";
    }
}
