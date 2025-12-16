package com.chat.webSocket;

/**
 * Simple payload object used for websocket messages.
 */
public class Payload {
	public int messageId;
	public int chatId;
	public int senderId;
	public String content;
	public String type;
	public String status;
	public String sentAt;
	public String seenAt;

	public Payload() {}

	public Payload(int messageId, int chatId, int senderId, String content, String type, String status, String sentAt, String seenAt) {
		this.messageId = messageId;
		this.chatId = chatId;
		this.senderId = senderId;
		this.content = content;
		this.type = type;
		this.status = status;
		this.sentAt = sentAt;
		this.seenAt = seenAt;
	}

	@Override
	public String toString() {
		return "Payload{" +
				"messageId=" + messageId +
				", chatId=" + chatId +
				", senderId=" + senderId +
				", content='" + content + '\'' +
				", type='" + type + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
