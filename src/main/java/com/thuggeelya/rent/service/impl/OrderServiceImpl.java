package com.thuggeelya.rent.service.impl;

import com.thuggeelya.rent.dto.OrderDTO;
import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.model.Order;
import com.thuggeelya.rent.model.enums.EOrderStatus;
import com.thuggeelya.rent.repository.OrderRepository;
import com.thuggeelya.rent.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        return repository.saveAndFlush(order);
    }

    @Override
    @CachePut("orders")
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    @Override
    @CachePut("order")
    public Order findOrderById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Order is not found."));
    }

    @Override
    @CachePut("user_orders")
    public List<Order> findCertainUserOrders(AppUser user, EOrderStatus status) {
        return repository.findCertainUserOrders(user, status);
    }

    @Override
    public List<Order> findOrdersByStatus(EOrderStatus status) {
        return repository.findOrdersByStatus(status);
    }

    @Override
    public List<Order> findOrdersInRange(@Nullable AppUser user, Date start, Date end) {
        if (user == null) {
            return repository.findOrdersByOrderDateBetween(offsetDateTime(start), offsetDateTime(end));
        }

        return repository.findOrdersByUserAndOrderDateBetween(user, offsetDateTime(start), offsetDateTime(end));
    }

    @Override
    public List<Order> findOrdersAfter(@Nullable AppUser user, Date date) {
        if (user == null) {
            return repository.findOrdersByOrderDateAfter(offsetDateTime(date));
        }

        return repository.findOrdersByUserAndOrderDateAfter(user, offsetDateTime(date));
    }

    @Override
    public List<Order> findOrdersBefore(@Nullable AppUser user, Date date) {
        if (user == null) {
            return repository.findOrdersByOrderDateBefore(offsetDateTime(date));
        }

        return repository.findOrdersByUserAndOrderDateBefore(user, offsetDateTime(date));
    }

    private OffsetDateTime offsetDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Order updateOrderById(Long id, OrderDTO orderDTO) {
        Order order = findOrderById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(orderDTO, order);
        return repository.saveAndFlush(order);
    }
}
