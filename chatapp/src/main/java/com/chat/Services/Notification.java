package com.chat.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Notification center that implements a lightweight observer pattern.
 * Clients can subscribe to notifications for a specific userId or subscribe
 * as global listeners. This keeps changes minimal and avoids new top-level types.
 */
public class Notification {
    public interface Listener {
        void onNotify(String userId, String message);
    }

    private static Notification instance;

    // per-user listeners
    private final Map<String, List<Listener>> listenersByUser;
    // global listeners
    private final List<Listener> globalListeners;

    private Notification() {
        listenersByUser = new HashMap<>();
        globalListeners = new ArrayList<>();
    }

    public static synchronized Notification getInstance() {
        if (instance == null) instance = new Notification();
        return instance;
    }

    public synchronized void subscribe(String userId, Listener listener) {
        if (userId == null) {
            globalListeners.add(listener);
            return;
        }
        listenersByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(listener);
    }

    public synchronized void unsubscribe(String userId, Listener listener) {
        if (userId == null) {
            globalListeners.remove(listener);
            return;
        }
        List<Listener> list = listenersByUser.get(userId);
        if (list != null) list.remove(listener);
    }

    public synchronized List<Listener> getListenersFor(String userId) {
        List<Listener> result = new ArrayList<>(globalListeners);
        List<Listener> per = listenersByUser.get(userId);
        if (per != null) result.addAll(per);
        return Collections.unmodifiableList(result);
    }

    public void notifyUser(String userId, String message) {
        for (Listener l : getListenersFor(userId)) {
            try { l.onNotify(userId, message); } catch (Exception ignored) {}
        }
    }

    public void broadcast(String message) {
        // notify all listeners (global + per-user)
        // copy keys to avoid concurrency issues
        List<Listener> copy = new ArrayList<>(globalListeners);
        for (List<Listener> ls : listenersByUser.values()) copy.addAll(ls);
        for (Listener l : copy) {
            try { l.onNotify(null, message); } catch (Exception ignored) {}
        }
    }
}