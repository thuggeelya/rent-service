package com.thuggeelya.rent.controller;

import com.thuggeelya.rent.dto.AppUserDTO;
import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.service.OrderService;
import com.thuggeelya.rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;
    final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> users() {
        List<AppUser> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> userById(@PathVariable Long id) {
        AppUser user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody AppUserDTO userDTO) {
        AppUser user = userService.updateUserById(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> userOrders(@PathVariable Long id) {
        AppUser user = userService.findUserById(id);
        return ResponseEntity.ok(user.getOrders());
    }

    @GetMapping("/{id}/orders/{dateFrom}")
    public ResponseEntity<?> userOrdersFrom(@PathVariable Long id,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom) {
        AppUser user = userService.findUserById(id);
        return ResponseEntity.ok(orderService.findOrdersAfter(user, dateFrom));
    }

    @GetMapping("/{id}/orders/{dateTo}")
    public ResponseEntity<?> userOrdersTo(@PathVariable Long id,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        AppUser user = userService.findUserById(id);
        return ResponseEntity.ok(orderService.findOrdersBefore(user, dateTo));
    }

    @GetMapping("/{id}/orders/{dateFrom}-to-{dateTo}")
    public ResponseEntity<?> userOrdersBetween(@PathVariable Long id,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        AppUser user = userService.findUserById(id);
        return ResponseEntity.ok(orderService.findOrdersInRange(user, dateFrom, dateTo));
    }
}
