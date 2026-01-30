package org.ranc.mybatis_audit.model;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.mybatis.audit.model.AuditAware;

public class ItemTest {

    @Test
    public void testItem() {
        Field[] fields = Item.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field: " + field.getName() + ", Type: " + field.getType().getSimpleName());
        }

        Field[] superFields = Item.class.getSuperclass().getDeclaredFields();
        for (Field field : superFields) {
            System.out.println("Superclass Field: " + field.getName() + ", Type: " + field.getType().getSimpleName());
        }
    }

    @Test
    public void testAUdit() {
        Item item = new Item(1L, "apple", 100, 1L, 1, "creator", "updator", OffsetDateTime.now(), OffsetDateTime.now());
        if (item instanceof AuditAware) {
            AuditAware auditAware = (AuditAware) item;
            System.out.println("Created By: " + auditAware.getCreatedBy());
            System.out.println("Updated By: " + auditAware.getUpdatedBy());
            System.out.println("Created At: " + auditAware.getCreatedAt());
            System.out.println("Updated At: " + auditAware.getUpdatedAt());
        }
    }
}
