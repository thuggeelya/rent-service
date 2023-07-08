package com.thuggeelya.rent.repository;

import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.model.Order;
import com.thuggeelya.rent.model.enums.EOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user = :user and o.status = :status")
    List<Order> findCertainUserOrders(@Param("user") AppUser user, @Param("status") EOrderStatus status);

    List<Order> findOrdersByStatus(EOrderStatus status);

    List<Order> findOrdersByOrderDateBetween(OffsetDateTime start, OffsetDateTime end);

    List<Order> findOrdersByUserAndOrderDateBetween(AppUser user, OffsetDateTime start, OffsetDateTime end);

    List<Order> findOrdersByOrderDateAfter(OffsetDateTime orderDate);

    List<Order> findOrdersByUserAndOrderDateAfter(AppUser user, OffsetDateTime orderDate);

    List<Order> findOrdersByOrderDateBefore(OffsetDateTime orderDate);

    List<Order> findOrdersByUserAndOrderDateBefore(AppUser user, OffsetDateTime orderDate);

    List<Order> findOrdersByOrderDate(OffsetDateTime orderDate);

    List<Order> findOrdersByUserAndOrderDate(AppUser user, OffsetDateTime orderDate);
}
