package org.ranc.mybatis_audit.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.model.Item;
import org.ranc.mybatis_audit.model.ItemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceImplTest {

    @Autowired
    private ItemService service;

    @Autowired
    private ItemLogService logService;

    @Test
    public void testInsert() {
        Item item = new Item(null, "Abocado", 190, null, null, null, null, null);
        service.insert(item);
        testFindAll();
    }

    @Test
    public void testDelete() {
        Item item = service.findById(10L);
        service.delete(item);
        testFindAll();
    }

    @Test
    public void testInsertBatch() {
        Item item1 = new Item(null, "Banana", 120, null, null, null, null, null);
        Item item2 = new Item(null, "Cherry", 300, null, null, null, null, null);
        Item item3 = service.findById(10L);
        service.insertBatch(List.of(item1, item2));
        item1.setPrice(150);
        item2.setPrice(350);
        service.update(item1);
        service.update(item2);
        service.delete(item3);
        testFindAll();
    }

    public void testFindAll() {
        List<Item> items = service.findAll();
        List<ItemLog> logs = logService.findAll();
        System.out.println("=== Items ===");
        items.forEach(System.out::println);
        System.out.println("=== Item Logs ===");
        logs.forEach(System.out::println);
    }
}
