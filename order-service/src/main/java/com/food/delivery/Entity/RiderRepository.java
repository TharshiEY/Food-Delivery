package com.food.delivery.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {

    @Transactional
    @Query("select o from Rider o where upper(o.riderId) like upper(:riderId)")
    Rider findByOrderIdIgnoreCase(@Param("riderId") String riderId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rider r WHERE r.riderId = :uniqueOrderId")
    boolean existsByOrderId(String uniqueOrderId);

}
