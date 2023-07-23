package com.food.delivery.Mapper;


import com.food.delivery.Dto.RiderDto;
import com.food.delivery.Entity.Rider;
import org.springframework.stereotype.Component;

@Component
public class RiderMapper {
    public static Rider mapToRider(RiderDto riderDto) {
        if (riderDto == null) {
            throw new IllegalArgumentException("riderDto cannot be null");
        }
        Rider rider = new Rider();
//        rider.setId(riderDto.getId());
        rider.setRiderId(riderDto.getRiderId());
        rider.setName(riderDto.getName());
        rider.setContact(riderDto.getContact());
        rider.setVehicleNumber(riderDto.getVehicleNumber());
        return rider;
    }

    public static RiderDto mapToRiderDto(Rider rider) {
        if (rider == null) {
            throw new IllegalArgumentException("Rider cannot be null");
        }
        RiderDto riderDto = new RiderDto();
        riderDto.setId(rider.getId());
        riderDto.setRiderId(rider.getRiderId());
        riderDto.setName(rider.getName());
        riderDto.setContact(rider.getContact());
        riderDto.setVehicleNumber(rider.getVehicleNumber());
        return riderDto;
    }
}
