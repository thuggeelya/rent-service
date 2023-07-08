package com.thuggeelya.rent.service.impl;

import com.thuggeelya.rent.dto.ItemDTO;
import com.thuggeelya.rent.model.Item;
import com.thuggeelya.rent.repository.ItemRepository;
import com.thuggeelya.rent.service.ItemService;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
public class ItemServiceImpl implements ItemService {

    final ItemRepository repository;

    @Autowired
    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Item save(Item item) {
        return repository.saveAndFlush(item);
    }

    @Override
    @CachePut("items")
    public List<Item> findAllItems() {
        return repository.findAll();
    }

    @Override
    @CachePut("item")
    public Item findItemById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Item is not found."));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Item updateItemById(Long id, ItemDTO itemDTO) {
        Item item = findItemById(id);
        log.debug("Item found with id: " + id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(itemDTO, item);
        return repository.saveAndFlush(item);
    }
}
