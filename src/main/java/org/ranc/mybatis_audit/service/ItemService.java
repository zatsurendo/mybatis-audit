package org.ranc.mybatis_audit.service;

import java.util.List;

import org.ranc.mybatis_audit.model.Item;

public interface ItemService {
    int createItem(Item item);
    Item findById(Long id);
    List<Item> findAll();
}
