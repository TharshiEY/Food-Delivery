package com.delivery.riderservice.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Transactional
    @Query("select d from Delivery d where upper(d.orderId) like upper(:orderId) and upper(d.riderId) like upper(:riderId) and d.status = true")
    Delivery findByOrderIdLikeIgnoreCaseAndRiderIdLikeIgnoreCaseAndStatusTrue(@NonNull String orderId, @NonNull String riderId);
    Delivery findByIdAndStatusTrue(@NonNull long id);

}
