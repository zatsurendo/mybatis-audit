package org.ranc.mybatis_audit.service;

import java.util.List;

import org.ranc.mybatis_audit.model.Item;
import org.ranc.mybatis_audit.repository.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper mapper;

    @Transactional
    @Override
    public int insert(Item item) {
        if (item.getId() != null) {
            throw new IllegalArgumentException("New item cannot already have an ID");
        }
        return mapper.insert(item);
    }
    
    @Transactional
    @Override
    public int insertBatch(List<Item> items) {
        for (Item item : items) {
            if (item.getId() != null) {
                throw new IllegalArgumentException("New item cannot already have an ID");
            }
        }
        return mapper.insertBatch(items);
    }

    @Transactional
    @Override
    public int update(Item item) {
        if (item.getId() == null) {
            throw new IllegalArgumentException("Item ID cannot be null for update");
        }
        return mapper.update(item);
    }

    @Override
    public Item findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return mapper.findAll();
    }

    @Transactional
    @Override
    public int delete(Item item) {
        return mapper.delete(item);
    }

}
