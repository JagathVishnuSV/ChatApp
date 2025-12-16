package com.chat.chats.models;

public abstract class Chat {
    public int chatid;
    public String chat_name;
    public String created_at;

    public Chat(int chatid, String chat_name, String created_at) {
        this.chatid = chatid;
        this.chat_name = chat_name;
        this.created_at = created_at;
    }

    public int getChatId() {
        return this.chatid;
    }

    public String getChatName() {
        return this.chat_name;
    }

    public String getCreatedAt() {
        return this.created_at;
    }

    public void setChatName(String chat_name) {
        this.chat_name = chat_name;
    }
}

