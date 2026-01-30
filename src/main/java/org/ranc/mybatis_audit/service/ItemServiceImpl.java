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
    public int createItem(Item item) {
        return mapper.insert(item);
    }

    @Override
    public Item findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return mapper.findAll();
    }
}
