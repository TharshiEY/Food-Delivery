package com.food.delivery.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.delivery.Dto.RiderDto;

public interface RiderService {
    public RiderDto createRider(RiderDto riderDto) throws JsonProcessingException;
    public String testRabbitMQ(RiderDto riderDto);
}
