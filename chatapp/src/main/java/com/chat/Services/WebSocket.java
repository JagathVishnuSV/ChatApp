package com.chat.Services;

import com.chat.message.models.Message;
import com.chat.message.repository.MessageRepositoryMongoDB;

public class WebSocket {
    private final MessageRepositoryMongoDB messageRepository;

    public WebSocket() {
        this.messageRepository = new MessageRepositoryMongoDB();
    }

    public void sendMessage(Message message) {
        // Save the message to the database
        messageRepository.save(message);

        // Simulate sending the message over WebSocket
        System.out.println("Message sent via WebSocket: " + message.getMessageContent());
    }

    public void receiveMessage(String messageId) {
        // Fetch the message from the database
        Message message = messageRepository.findById(messageId);
        if (message != null) {
            System.out.println("Message received via WebSocket: " + message.getMessageContent());
        } else {
            System.out.println("Message not found.");
        }
    }
}