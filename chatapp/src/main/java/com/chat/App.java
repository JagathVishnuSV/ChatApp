package com.chat;

import com.chat.config.DatabaseConfig;
import com.chat.webSocket.SessionManager;
import com.chat.webSocket.Payload;
import com.chat.Services.Messaging;
import com.chat.Services.MediaServices;
import com.chat.Services.Notification;
import com.chat.message.models.Message;

public class App {
    public static void main(String[] args) throws Exception {
    
        DatabaseConfig.getDatabase();
        
        Messaging messagingService = new Messaging();
        MediaServices mediaServices = new MediaServices();

        SessionManager.Session Jagath = message -> System.out.println("[Jagath session] " + message);
        SessionManager.Session Vishnu = message -> System.out.println("[Vishnu session] " + message);

        SessionManager.getInstance().registerSession("201", Jagath);
        SessionManager.getInstance().registerSession("202", Vishnu);

        Notification.getInstance().subscribe(null, (uid, msg) -> System.out.println("[GlobalListener] " + msg));

        Message message = new Message(1, 101, 201, "Hello, Jagath!", "text", "sent", "2025-12-16T10:00:00", "2025-12-16T10:01:00");
        messagingService.sendMessage(message);

        Notification.getInstance().notifyUser("202", "You have a new direct message from user 201");

        Payload p = new Payload(message.getMessageId(), message.getChatId(), message.getSenderId(),
                                message.getMessageContent(), message.getMessageType(), message.getStatus(),
                                message.getSentAt(), message.getSeenAt());
        System.out.println("Payload created: " + p);

        mediaServices.uploadMedia("path/to/file.jpg");
    }
}