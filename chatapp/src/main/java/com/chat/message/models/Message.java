package com.chat.message.models;

public class Message{
    public int messageid;
    public int chatid;
    public int senderid;
    public String message_content;
    public String message_type;
    public String status;
    public String sent_at;
    public String seen_at;
    
    public Message(int messageid, int chatid, int senderid, String message_content, String message_type, String status,String seen_at,String sent_at) {
        this.messageid = messageid;
        this.chatid = chatid;
        this.senderid = senderid;
        this.message_content = message_content;
        this.message_type = message_type;
        this.status = status;
        this.sent_at = sent_at;
        this.seen_at = seen_at;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMessageId() {
        return this.messageid;
    }

    public int getChatId() {
        return this.chatid;
    }

    public int getSenderId() {
        return this.senderid;
    }

    public String getMessageContent() {
        return this.message_content;
    }

    public String getMessageType() {
        return this.message_type;
    }

    public String getStatus() {
        return this.status;
    }

    public String getSentAt() {
        return this.sent_at;
    }

    public String getSeenAt() {
        return this.seen_at;
    }
    
}
