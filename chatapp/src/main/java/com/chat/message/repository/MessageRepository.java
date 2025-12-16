package com.chat.message.repository;

import com.chat.message.models.Message;
import java.util.List;

public interface MessageRepository {
    Message findById(String id);
    List<Message> findAll();
    void save(Message message);
    void update(Message message);
    void delete(String id);
}