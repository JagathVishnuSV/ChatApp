package com.chat;

import com.chat.Services.Messaging;
import com.chat.Services.WebSocket;
import com.chat.Services.MediaServices;
import com.chat.message.models.Message;

public class App {
    public static void main(String[] args) {
        // Initialize services
        Messaging messagingService = new Messaging();
        WebSocket webSocketService = new WebSocket();
        MediaServices mediaServices = new MediaServices();

        // Example: Send a message
        Message message = new Message(1, 101, 201, "Hello, World!", "text", "sent", "2025-12-16T10:00:00", "2025-12-16T10:01:00");
        messagingService.sendMessage(message);

        // Example: Use WebSocket service
        webSocketService.sendMessage(message);
        webSocketService.receiveMessage("1");

        // Example: Upload and download media
        mediaServices.uploadMedia("path/to/file.jpg");
        mediaServices.downloadMedia("12345");
    }
}