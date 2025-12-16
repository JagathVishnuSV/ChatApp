package com.chat.webSocket;

import com.chat.config.SocketConfig;
import com.chat.user.repository.UserRepositoryMongoDB;
import com.chat.user.models.User;
import com.chat.Services.Messaging;
import com.chat.message.models.Message;
import com.chat.Services.Notification;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket-based ChatSocketHandler using Java-WebSocket library.
 * Supports simple text commands (CREATE, LOGIN, MSG, QUIT) sent as text frames.
 */
public class ChatSocketHandler extends WebSocketServer {
    private final Messaging messaging = new Messaging();
    private final UserRepositoryMongoDB userRepo = new UserRepositoryMongoDB();
    private final AtomicInteger msgIdCounter = new AtomicInteger(1);

    public ChatSocketHandler() {
        super(new InetSocketAddress(SocketConfig.getInstance().getPort()));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome. Use CREATE:<id>:<name>:<phone> | LOGIN:<id> | MSG:<fromId>:<toId>:<text> | QUIT");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // no-op; sessions are unregistered when clients call LOGOUT or disconnect
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            if (message == null) return;
            String line = message.trim();
            if (line.isEmpty()) return;

            if (line.startsWith("CREATE:")) {
                String[] parts = line.split(":", 4);
                if (parts.length < 3) {
                    conn.send("ERR: CREATE requires userid and username");
                    return;
                }
                String uidStr = parts[1].trim();
                String username = parts.length >= 3 ? parts[2].trim() : "";
                String phone = parts.length >= 4 ? parts[3].trim() : "";
                try {
                    int uid = Integer.parseInt(uidStr);
                    String now = Instant.now().toString();
                    User u = new User(uid, username, phone, "", "", now, "");
                    userRepo.save(u);
                    conn.send("OK:USER_CREATED:" + uid);
                } catch (NumberFormatException e) {
                    conn.send("ERR: invalid userid");
                }
                return;
            }

            if (line.startsWith("LOGIN:")) {
                String uid = line.substring(6).trim();
                User user = userRepo.findById(uid);
                if (user == null) {
                    conn.send("ERR: user not found");
                    return;
                }

                // register Session that sends via this WebSocket
                SessionManager.Session session = msg -> conn.send(msg);
                SessionManager.getInstance().registerSession(uid, session);
                conn.send("OK:LOGGED_IN:" + uid);
                return;
            }

            if (line.startsWith("MSG:")) {
                String rest = line.substring(4);
                String[] maybe = rest.split(":", 3);
                if (maybe.length == 3) {
                    String possibleFrom = maybe[0].trim();
                    String possibleTo = maybe[1].trim();
                    String possibleText = maybe[2].trim();
                    try {
                        int mid = msgIdCounter.getAndIncrement();
                        int fromInt = Integer.parseInt(possibleFrom);
                        int toInt = Integer.parseInt(possibleTo);
                        String now = Instant.now().toString();
                        Message m = new Message(mid, 0, fromInt, possibleText, "text", "sent", now, now);
                        messaging.sendMessage(m);
                        // notify target
                        Notification.getInstance().notifyUser(possibleTo, "DM from " + possibleFrom + ": " + possibleText);
                        conn.send("SENT:" + mid);
                    } catch (NumberFormatException ex) {
                        conn.send("ERR: invalid numeric ids in MSG");
                    }
                } else {
                    conn.send("ERR: MSG requires format MSG:<fromId>:<toId>:<text>");
                }
                return;
            }

            if (line.equalsIgnoreCase("QUIT")) {
                try { conn.close(); } catch (Exception ignored) {}
                return;
            }

            conn.send("ERR: unknown command");
        } catch (Exception e) {
            conn.send("ERR: exception processing message");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started on " + getAddress());
    }
}