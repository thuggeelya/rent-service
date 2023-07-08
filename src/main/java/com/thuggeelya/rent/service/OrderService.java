package com.thuggeelya.rent.service;

import com.thuggeelya.rent.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);

    List<Order> findAllOrders();

    //
}
