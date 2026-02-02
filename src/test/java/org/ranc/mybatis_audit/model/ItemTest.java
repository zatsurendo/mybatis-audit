package org.ranc.mybatis_audit.model;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

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

}
