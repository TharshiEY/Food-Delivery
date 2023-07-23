package com.food.delivery.Controller;

import com.food.delivery.Dto.OrderDto;
import com.food.delivery.Dto.ResponseDto;
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
    public ResponseDto<String> placeOrder(@RequestBody OrderDto orderDTO){
        log.info("OrderController.placeOrder(OrderDTO orderDTO) Invoked.");
        String Dto = orderService.placeOrder(orderDTO);

        if (Dto != null) {
            return new ResponseDto<>(true, "Request Place Order Successfully",Dto);
        } else {
            return new ResponseDto<>(false, "Order not found", null);
        }
    }

    @GetMapping("/{orderId}")
    public boolean orderStatus(@PathVariable("orderId") String orderId){
        log.info("OrderController.orderStatus(String orderId) Invoked.");
        return orderService.orderStatus(orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseDto<OrderDto> updateOrderStatus(@PathVariable("orderId") String orderId, @RequestParam("status") String status){
        log.info("OrderController.updateOrderStatus() invoked." +orderId);
        OrderDto orderDto = orderService.updateOrderStatus(orderId, status);

        if (orderDto != null) {
            return new ResponseDto<>(true, "Order status found", orderDto);
        } else {
            return new ResponseDto<>(false, "Order status not found", null);
        }
    }
}
