package com.food.delivery.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.delivery.Dto.ResponseDto;
import com.food.delivery.Dto.RiderDto;
import com.food.delivery.Service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/rider")
public class RiderController {

    @Autowired
    RiderService riderService;

    @PostMapping()
    public ResponseDto<RiderDto> createRider(@RequestBody RiderDto riderDto) throws JsonProcessingException {
        log.info("RiderController.createRider() Invoked.");
        RiderDto dto = riderService.createRider(riderDto);
        if (dto != null) {
            return new ResponseDto<>(true, "Request Rider Successfully",dto);
        } else {
            return new ResponseDto<>(false, "Rider not found", null);
        }
    }

}