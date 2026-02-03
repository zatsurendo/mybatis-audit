package org.ranc.mybatis_audit.service;

import java.util.List;

import org.ranc.mybatis_audit.model.Item;

public interface ItemService {
    int insert(Item item);
    int insertBatch(List<Item> items);
    int update(Item item);
    Item findById(Long id);
    List<Item> findAll();
    int delete(Item item);
}
