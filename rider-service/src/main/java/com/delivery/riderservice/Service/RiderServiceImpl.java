package com.delivery.riderservice.Service;

import com.delivery.riderservice.Common.GenerateUniqueValue;
import com.delivery.riderservice.Dto.RiderDto;
import com.delivery.riderservice.Entity.Rider;
import com.delivery.riderservice.Entity.RiderRepository;
import com.delivery.riderservice.Mapper.RiderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderMapper riderMapper;

    @Autowired
    GenerateUniqueValue generateUniqueValue;

    @Override
    public RiderDto createRider(RiderDto riderDto) {
        log.info("RiderServiceImpl.createRider() Invoked.");
        Rider rider = riderMapper.mapToRider(riderDto);
        rider.setRiderId(generateUniqueValue.generateUniqueOrderId());
        Rider saverider = riderRepository.save(rider);
        RiderDto dto = riderMapper.mapToRiderDto(saverider);
        return dto;
    }

}
