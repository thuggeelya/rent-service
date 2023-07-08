package com.thuggeelya.rent.service;

import com.thuggeelya.rent.dto.ItemDTO;
import com.thuggeelya.rent.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);

    List<Item> findAllItems();

    Item findItemById(Long id);

    Item updateItemById(Long id, ItemDTO itemDTO);
}
