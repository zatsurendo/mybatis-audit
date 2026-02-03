package org.ranc.mybatis_audit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ranc.mybatis_audit.model.Item;
import org.ranc.mybatis_audit.mybatis.mapper.AuditableMapper;

@Mapper
public interface ItemMapper extends AuditableMapper<Item> {
    
    Item findById(Long id);
    Item findByName(String name);
    List<Item> findAll();
    //int insert(Item item);
    //int insertBatch(List<Item> items);
    //int update(Item item);
    //int delete(Item item);
}
