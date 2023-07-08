package com.thuggeelya.rent.controller;

import com.thuggeelya.rent.dto.ItemDTO;
import com.thuggeelya.rent.model.Item;
import com.thuggeelya.rent.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/items")
public class ItemController {

    final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<?> items() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> itemById(@PathVariable Long id) {
        Item item = itemService.findItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        Item item = itemService.updateItemById(id, itemDTO);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{id}/users")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> itemUsersHistory(@PathVariable Long id) {
        Item item = itemService.findItemById(id);
        return ResponseEntity.ok(item.getUsers());
    }
}
