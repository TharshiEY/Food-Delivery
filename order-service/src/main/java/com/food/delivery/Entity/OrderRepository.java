package com.food.delivery.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying
    @Query("update Order o set o.status = :status where upper(o.orderId) like upper(:orderId)")
    void updateStatus(@Param("status") String status, @Param("orderId") String orderId);

    @Query("select o from Order o where upper(o.orderId) like upper(:orderId)")
    Order findByOrderIdIgnoreCase(@Param("orderId") String orderId);
}
