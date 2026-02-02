package org.ranc.mybatis_audit.service;

import java.util.List;

import org.ranc.mybatis_audit.model.ItemLog;

public interface ItemLogService {
    
    ItemLog findById(Long id);
    ItemLog findByName(String name);
    List<ItemLog> findAll();
    int insert(ItemLog item);
    int insertBatch(List<ItemLog> items);
    int update(ItemLog item);
    int delete(ItemLog item);
}
