package org.ranc.mybatis_audit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ranc.mybatis_audit.model.ItemLog;

@Mapper
public interface ItemLogMapper {
    
    ItemLog findById(Long id);
    ItemLog findByName(String name);
    List<ItemLog> findAll();
    int insert(ItemLog item);
    int insertBatch(List<ItemLog> items);
    int update(ItemLog item);
    int delete(ItemLog item);
}
