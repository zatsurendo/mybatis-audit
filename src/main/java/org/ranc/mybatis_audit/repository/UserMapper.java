package org.ranc.mybatis_audit.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ranc.mybatis_audit.model.User;

@Mapper
public interface UserMapper {
    
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    int insert(User user);
    int update(User user);
    int delete(Long id);
}
