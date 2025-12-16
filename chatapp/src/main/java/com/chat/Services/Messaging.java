package com.chat.Services;

import com.chat.message.models.Message;
import com.chat.message.repository.MessageRepositoryMongoDB;

import java.util.List;

public class Messaging {
    private final MessageRepositoryMongoDB messageRepository;
    private final Notification notificationService;

    public Messaging() {
        this.messageRepository = new MessageRepositoryMongoDB();
        this.notificationService = new Notification();
    }

    public void sendMessage(Message message) {
        // Save the message
        messageRepository.save(message);

        // Notify the receiver
        notificationService.sendNotification(String.valueOf(message.getSenderId()), "New message received!");

        System.out.println("Message sent: " + message.getMessageContent());
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}