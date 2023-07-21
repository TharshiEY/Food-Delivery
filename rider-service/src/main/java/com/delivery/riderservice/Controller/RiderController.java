package com.delivery.riderservice.Controller;

import com.delivery.riderservice.Dto.ResponseDto;
import com.delivery.riderservice.Dto.RiderDto;
import com.delivery.riderservice.Service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/rider")
public class RiderController {

    @Autowired
    RiderService riderService;

    @PostMapping()
    public ResponseDto<RiderDto> createRider(@RequestBody RiderDto riderDto){
        log.info("RiderController.createRider() Invoked.");
        RiderDto dto = riderService.createRider(riderDto);
        if (dto != null) {
            return new ResponseDto<>(true, "Request Rider Successfully",dto);
        } else {
            return new ResponseDto<>(false, "Rider not found", null);
        }
    }

}
