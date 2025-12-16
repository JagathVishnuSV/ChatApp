package com.chat.Services;

import com.chat.message.models.Message;
import java.util.List;

/**
 * Messaging coordinates sending messages using the WebSocket proxy and
 * leverages Notification for targeted notifications. The real persistence
 * is handled inside the WebSocket Real implementation to avoid duplicate saves.
 */
public class Messaging {
    private final WebSocket webSocket;

    public Messaging() {
        this.webSocket = new WebSocket();
    }

    public void sendMessage(Message message) {
        if (message == null) return;
        // Delegate to WebSocket proxy which persists and broadcasts
        webSocket.sendMessage(message);

        // targeted notify example: notify sender that message was sent
        Notification.getInstance().notifyUser(String.valueOf(message.getSenderId()), "Message sent: " + message.getMessageContent());
    }

    public List<Message> getAllMessages() {
        // For simplicity reuse repository here if needed
        return new com.chat.message.repository.MessageRepositoryMongoDB().findAll();
    }
}