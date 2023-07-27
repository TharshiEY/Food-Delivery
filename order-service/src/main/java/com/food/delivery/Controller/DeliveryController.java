package com.food.delivery.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.delivery.Dto.DeliveryDto;
import com.food.delivery.Dto.ResponseDto;
import com.food.delivery.Service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping()
    public ResponseDto<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) throws JsonProcessingException {
        log.info("RiderController.createRider() Invoked.");
        DeliveryDto dto = deliveryService.createDelivery(deliveryDto);
        if (dto != null) {
            return new ResponseDto<>(true, "Delivery details",dto);
        } else {
            return new ResponseDto<>(false, "Delivery not found", null);
        }
    }
}
