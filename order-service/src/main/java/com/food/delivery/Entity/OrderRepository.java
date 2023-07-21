package com.food.delivery.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where upper(o.status) like upper(:status) order by o.orderId")
    List<Order> FindFailureOrder(String status);
    @Transactional
    @Modifying
    @Query("update Order o set o.status = :status where upper(o.orderId) like upper(:orderId)")
    void updateStatus(@Param("status") String status, @Param("orderId") String orderId);

    @Query("select o from Order o where upper(o.orderId) like upper(:orderId)")
    Order findByOrderIdIgnoreCase(@Param("orderId") String orderId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Order r WHERE r.orderId = :uniqueOrderId")
    boolean existsByOrderId(String uniqueOrderId);
}
