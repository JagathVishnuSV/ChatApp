package com.chat.chats.repository;

import com.chat.chats.models.Chat;
import java.util.List;

public interface ChatRepository {
    Chat findById(String id);
    List<Chat> findAll();
    void save(Chat chat);
    void update(Chat chat);
    void delete(String id);
}