package com.delivery.riderservice.Service;

import com.delivery.riderservice.Dto.RiderDto;
import com.delivery.riderservice.Entity.Rider;
import com.delivery.riderservice.Entity.RiderRepository;
import com.delivery.riderservice.Mapper.RiderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderMapper riderMapper;
    private final ObjectMapper objectMapper;

    private final MessageConverter messageConverter = new SimpleMessageConverter();

    public RiderServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "Save_Rider_Queue")
    public void consumeRider(String message){
        log.info("RiderServiceImpl.consumeRider() Invoked"+ message);
        try {
            RiderDto dto = objectMapper.readValue(message, RiderDto.class);
            createRider(dto);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public RiderDto createRider(RiderDto riderDto) {
        log.info("RiderServiceImpl.createRider() Invoked.");
        Rider rider = riderMapper.mapToRider(riderDto);
        rider.setRiderId(riderDto.getRiderId());
        Rider saverider = riderRepository.save(rider);
        RiderDto dto = riderMapper.mapToRiderDto(saverider);
        return dto;
    }

}
