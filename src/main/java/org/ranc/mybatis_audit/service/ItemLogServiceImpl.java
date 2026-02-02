package org.ranc.mybatis_audit.service;

import java.util.List;

import org.ranc.mybatis_audit.model.ItemLog;
import org.ranc.mybatis_audit.repository.ItemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemLogServiceImpl implements ItemLogService {

    @Autowired
    private ItemLogMapper mapper;

    @Override
    public ItemLog findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public ItemLog findByName(String name) {
        return mapper.findByName(name);
    }

    @Override
    public List<ItemLog> findAll() {
        return mapper.findAll();
    }

    @Override
    public int insert(ItemLog item) {
        return mapper.insert(item);
    }

    @Override
    public int insertBatch(List<ItemLog> items) {
        return mapper.insertBatch(items);
    }

    @Override
    public int update(ItemLog item) {
        return mapper.update(item);
    }

    @Override
    public int delete(ItemLog item) {
        return mapper.delete(item);
    }
    
}
