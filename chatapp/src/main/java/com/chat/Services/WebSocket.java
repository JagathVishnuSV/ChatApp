package com.chat.Services;

import com.chat.message.models.Message;
import com.chat.message.repository.MessageRepositoryMongoDB;

/**
 * WebSocket class acts as a proxy to the real websocket implementation.
 * It provides light access control/logging and delegates persistence and
 * delivery to the real implementation. Clients instantiate `WebSocket` as
 * before and receive a proxy instance.
 */
public class WebSocket {
    // the proxy holds the real implementation
    private final RealWebSocket real;

    public WebSocket() {
        this.real = new RealWebSocket();
    }

    // Proxy method: perform lightweight checks/logging then delegate
    public void sendMessage(Message message) {
        // access control or validation could go here
        if (message == null) return;
        System.out.println("[WebSocket Proxy] sending message id=" + message.getMessageId());
        real.sendMessage(message);
    }

    // Proxy for receiving messages (delegates to real)
    public void receiveMessage(String messageId) {
        System.out.println("[WebSocket Proxy] receiving message id=" + messageId);
        real.receiveMessage(messageId);
    }

    // Private real implementation that handles persistence and notification
    private static class RealWebSocket {
        private final MessageRepositoryMongoDB messageRepository;

        RealWebSocket() {
            this.messageRepository = new MessageRepositoryMongoDB();
        }

        void sendMessage(Message message) {
            // persist message
            messageRepository.save(message);

            // Simple delivery simulation: broadcast to all subscribers via Notification
            Notification.getInstance().broadcast("New message: " + message.getMessageContent());

            System.out.println("[RealWebSocket] Message persisted and broadcast: " + message.getMessageContent());
        }

        void receiveMessage(String messageId) {
            Message message = messageRepository.findById(messageId);
            if (message != null) {
                System.out.println("[RealWebSocket] Message received: " + message.getMessageContent());
            } else {
                System.out.println("[RealWebSocket] Message not found: " + messageId);
            }
        }
    }
}