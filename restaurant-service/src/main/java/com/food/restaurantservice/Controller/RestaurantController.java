package com.food.restaurantservice.Controller;

import com.food.restaurantservice.Dto.ResponseDTO;
import com.food.restaurantservice.Service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseDTO<String> getRestaurant(@PathVariable("id") String id){
        String s = restaurantService.getRestaurant(id);
        if (s != null) {
            return new ResponseDTO<>(true, "Order found", s);
        } else {
            return new ResponseDTO<>(false, "Order not found or Order is null", null);
        }
    }
}
