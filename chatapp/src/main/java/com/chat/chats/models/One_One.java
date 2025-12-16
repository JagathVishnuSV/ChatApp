package com.chat.chats.models;

public class One_One extends Chat {
    public int user1_id;
    public int user2_id;

    public One_One(int chatid, String chat_name, String created_at, int user1_id, int user2_id) {
        super(chatid, chat_name, created_at);
        this.user1_id = user1_id;
        this.user2_id = user2_id;
    }

    public int getUser1Id() {
        return this.user1_id;
    }

    public int getUser2Id() {
        return this.user2_id;
    }
}
