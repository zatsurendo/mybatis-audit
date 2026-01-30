package org.ranc.mybatis_audit.repository;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        userMapper.findAll().forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User(null, "s_n", "s_n@example.com");
        userMapper.insert(user);
        System.out.println("Inserted user ID: " + user.getId());
    }
}