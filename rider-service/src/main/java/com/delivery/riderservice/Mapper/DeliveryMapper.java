package com.delivery.riderservice.Mapper;

import com.delivery.riderservice.Dto.DeliveryDto;
import com.delivery.riderservice.Entity.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {
    public Delivery mapToDelivery(DeliveryDto deliveryDto){
        if (deliveryDto == null){
            throw new IllegalArgumentException("DeliveryDto cannot be null");
        }
        Delivery delivery = new Delivery();
        delivery.setId(deliveryDto.getId());
        delivery.setOrderId(deliveryDto.getOrderId());
        delivery.setRiderId(deliveryDto.getRiderId());
        delivery.setStatus(deliveryDto.getStatus());
        return delivery;
    }

    public DeliveryDto mapToDeliveryDto(Delivery delivery){
        if(delivery == null){
            throw new IllegalArgumentException("Delivery cannot be null");
        }
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(delivery.getId());
        deliveryDto.setRiderId(delivery.getRiderId());
        deliveryDto.setOrderId(delivery.getOrderId());
        deliveryDto.setStatus(delivery.getStatus());
        return deliveryDto;
    }
}
