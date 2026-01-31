package org.ranc.mybatis_audit.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //testCreateItemBatch();
        System.out.println("//// All Items ////");
        service.findAll().forEach(System.out::println);
    }

    @Test
    public void testCreateBatchAndFindItem() {
        // Test implementation goes here
        //testCreateItem();
        testCreateItemBatch();
        System.out.println("//// All Items ////");
        service.findAll().forEach(System.out::println);
    }

    @Test
    public void testUpdateMultiTimes() {
        for (int i = 0; i < 3; i++) {
            testUpdateItem();
        }
        System.out.println("//// All Items ////");
        service.findAll().forEach(System.out::println);
    }

    public void testUpdateItem() {
        Item item = service.findById(1L);
        item.setName("Updated times " + (item.getVersion() + 1));
        int result = service.updateItem(item);
    }

    public void testCreateItemBatch() {
        Item item1 = new Item(null, "Apple", 100);
        Item item2 = new Item(null, "Banana", 200);
        int result = service.createItemBatch(Stream.of(item1, item2).collect(Collectors.toList()));
        System.out.println("Batch insert result: " + result);
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
