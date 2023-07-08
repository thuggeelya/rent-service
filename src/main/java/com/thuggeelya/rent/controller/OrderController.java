package com.thuggeelya.rent.controller;

import com.thuggeelya.rent.dto.OrderDTO;
import com.thuggeelya.rent.model.Order;
import com.thuggeelya.rent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/orders")
public class OrderController {

    final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> orders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> orderById(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderById(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.updateOrderById(id, orderDTO);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{dateFrom}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> ordersFrom(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom) {
        return ResponseEntity.ok(orderService.findOrdersAfter(null, dateFrom));
    }

    @GetMapping("/{dateTo}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> ordersTo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        return ResponseEntity.ok(orderService.findOrdersAfter(null, dateTo));
    }

    @GetMapping("/{dateFrom}-to-{dateTo}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> ordersBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        return ResponseEntity.ok(orderService.findOrdersInRange(null, dateFrom, dateTo));
    }
}
