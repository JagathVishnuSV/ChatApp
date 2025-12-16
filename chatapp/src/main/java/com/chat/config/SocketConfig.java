package com.chat.config;
public class SocketConfig {
    private static SocketConfig instance;
    private int port = 8080;

    private SocketConfig() {}

    public static synchronized SocketConfig getInstance() {
        if (instance == null) {
            instance = new SocketConfig();

            // allow overriding port via system property or environment variable
            String prop = System.getProperty("chat.port");
            if (prop == null || prop.isEmpty()) prop = System.getenv("CHAT_PORT");
            if (prop != null) {
                try {
                    instance.port = Integer.parseInt(prop);
                } catch (NumberFormatException ignored) { /* keep default */ }
            }
        }
        return instance;
    }

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }
}