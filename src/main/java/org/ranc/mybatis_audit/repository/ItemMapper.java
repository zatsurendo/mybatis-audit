package org.ranc.mybatis_audit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ranc.mybatis_audit.model.Item;

@Mapper
public interface ItemMapper {
    
    Item findById(Long id);
    Item findByName(String name);
    List<Item> findAll();
    int insert(Item item);
    int update(Item item);
    int delete(Long id);
}
