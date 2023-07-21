package com.food.delivery.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRequestJsonRepository extends JpaRepository<OrderRequestJson, Long> {
    @Transactional
    @Query("select o from OrderRequestJson o where upper(o.orderId) like upper(:orderId)")
    OrderRequestJson findByOrderIdIgnoreCase(@Param("orderId") String orderId);

}
