package com.chat.config;

/**
 * Minimal socket configuration holder. Kept tiny to avoid adding frameworks.
 */
public class SocketConfig {
	private static SocketConfig instance;
	private int port = 8080;

	private SocketConfig() {}

	public static synchronized SocketConfig getInstance() {
		if (instance == null) instance = new SocketConfig();
		return instance;
	}

	public int getPort() { return port; }

	public void setPort(int port) { this.port = port; }
}
