package com.thuggeelya.rent.service;

import com.thuggeelya.rent.dto.OrderDTO;
import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.model.Order;
import com.thuggeelya.rent.model.enums.EOrderStatus;

import java.util.Date;
import java.util.List;

public interface OrderService {

    Order save(Order order);

    List<Order> findAllOrders();

    List<Order> findCertainUserOrders(AppUser user, EOrderStatus status);

    List<Order> findOrdersByStatus(EOrderStatus status);

    List<Order> findOrdersInRange(AppUser user, Date start, Date end);

    List<Order> findOrdersAfter(AppUser user, Date date);

    List<Order> findOrdersBefore(AppUser user, Date date);

    Order updateOrderById(Long id, OrderDTO orderDTO);

    Order findOrderById(Long id);
}
