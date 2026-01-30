package org.ranc.mybatis_audit.repository;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemMapperTest {
    
    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void testInsert() {
        Item i1 = new Item(null, "apple", 100);
        Item i2 = new Item(null, "orange", 200);
        itemMapper.insert(i1);
        itemMapper.insert(i2);
        itemMapper.findAll().forEach(System.out::println);
    }
}
