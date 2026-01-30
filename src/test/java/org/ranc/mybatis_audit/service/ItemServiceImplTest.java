package org.ranc.mybatis_audit.service;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceImplTest {
    
    @Autowired
    private ItemService service;

    @Test
    public void testCreateAndFindItem() {
        // Test implementation goes here
        testCreateItem();
        service.findAll().forEach(System.out::println);
    }

    public void testCreateItem() {
        Item item = new Item(null, "Orange", 150);
        int result = service.createItem(item);
        System.out.println("Insert result: " + result);
    }

    @Test
    public void testFindItem() {
        Item item = service.findById(1L);
        System.out.println(item.toString());
    }
}
