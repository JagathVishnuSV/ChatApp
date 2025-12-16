package com.chat.user.repository;

import com.chat.user.models.User;
import java.util.List;

public interface UserRepository {
    User findById(String id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(String id);
    User findByUsername(String username);
}