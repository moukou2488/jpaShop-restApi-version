package com.jpaproject.shop.service;

import com.jpaproject.shop.controller.item.ItemRequest;
import com.jpaproject.shop.domain.item.Item;
import com.jpaproject.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public void updateItem(Long itemId, ItemRequest itemRequest) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.changeParentField(itemRequest);
        findItem.changeChildField(itemRequest);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
