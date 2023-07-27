package com.food.delivery.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Transactional
    @Modifying
    @Query("update Delivery d set d.status = :status where upper(d.orderId) like upper(:orderId) and upper(d.riderId) like upper(:riderId)")
    void updateStatus(@NonNull Boolean status, @NonNull String orderId, @NonNull String riderId);

    @Transactional
    @Query("select d from Delivery d where upper(d.orderId) like upper(:orderId) and upper(d.riderId) like upper(:riderId) and d.status = true")
    Delivery findByOrderIdAndRiderIdAndStatusTrue(@NonNull String orderId, @NonNull String riderId);

    @Transactional
    @Query("select d from Delivery d where upper(d.orderId) like upper(:orderId) and upper(d.riderId) like upper(:riderId)")
    Delivery findByOrderIdAndRiderId(@NonNull String orderId, @NonNull String riderId);

    Delivery findByIdAndStatusTrue(@NonNull long id);

}
