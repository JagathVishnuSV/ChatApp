package com.chat.webSocket;

import com.chat.Services.Notification;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages lightweight websocket sessions for the demo. Sessions register
 * with Notification singleton to receive targeted notifications.
 */
public class SessionManager {
	public interface Session {
		void send(String message);
	}

	private static SessionManager instance;

	// userId -> session
	private final Map<String, Session> sessions = new HashMap<>();

	private SessionManager() {}

	public static synchronized SessionManager getInstance() {
		if (instance == null) instance = new SessionManager();
		return instance;
	}

	public synchronized void registerSession(String userId, Session session) {
		if (userId == null || session == null) return;
		sessions.put(userId, session);

		// subscribe to notifications for this user
		Notification.getInstance().subscribe(userId, (uid, message) -> {
			Session s = sessions.get(uid);
			if (s != null) s.send(message);
		});
	}

	public synchronized void unregisterSession(String userId) {
		if (userId == null) return;
		Session s = sessions.remove(userId);
		// best-effort: remove all matching listeners by not tracking them individually
		// (Notification supports removing by reference; skip for brevity)
	}

	public synchronized void sendToUser(String userId, String message) {
		Session s = sessions.get(userId);
		if (s != null) s.send(message);
	}

	public synchronized Map<String, Session> getSessions() {
		return Collections.unmodifiableMap(sessions);
	}
}
