package com.chat;

import com.chat.config.DatabaseConfig;
import com.chat.config.SocketConfig;
import com.chat.webSocket.ChatSocketHandler;

public class App {
    public static void main(String[] args) throws Exception {
        // optional CLI port handling (keeps existing SocketConfig behavior)
        if (args != null && args.length > 0) {
            String a = args[0];
            String val = a.startsWith("--port=") ? a.substring(7) : a;
            try { SocketConfig.getInstance().setPort(Integer.parseInt(val.trim())); } catch (Exception ignored) {}
        }

        // initialize DB
        DatabaseConfig.getDatabase();

        // start TCP socket server for frontend clients
        ChatSocketHandler server = new ChatSocketHandler();
        server.start();

        System.out.println("Server started on port " + SocketConfig.getInstance().getPort());
        Thread.currentThread().join(); // keep JVM alive
    }
}