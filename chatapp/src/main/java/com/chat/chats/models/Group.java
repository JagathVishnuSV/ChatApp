package com.chat.chats.models;
import java.util.*;

public class Group extends Chat {
    public int group_id;
    public String group_description;
    public String group_icon_url;
    public int chat_id;
    public String created_at;
    public List<Integer> member_ids;
    public List<Integer> admin_ids;

    public Group(int group_id, String group_description, String group_icon_url, int chat_id, String created_at, List<Integer> member_ids, List<Integer> admin_ids, String chat_name) {
        super(chat_id, chat_name, created_at);
        this.group_id = group_id;
        this.group_description = group_description;
        this.group_icon_url = group_icon_url;
        this.chat_id = chat_id;
        this.created_at = created_at;
        this.member_ids = member_ids;
        this.admin_ids = admin_ids;
    }

    public int getGroupId() {
        return this.group_id;
    }

    public String getGroupDescription() {
        return this.group_description;
    }

    public String getGroupIconUrl() {
        return this.group_icon_url;
    }

    public List<Integer> getMemberIds() {
        return this.member_ids;
    }

    public List<Integer> getAdminIds() {
        return this.admin_ids;
    }

    public void addMember(int user_id) {
        if (!this.member_ids.contains(user_id)) {
            this.member_ids.add(user_id);
        }
    }

    public void removeMember(int user_id) {
        this.member_ids.remove(Integer.valueOf(user_id));
        this.admin_ids.remove(Integer.valueOf(user_id));
    }

    public void addAdmin(int user_id) {
        if (this.member_ids.contains(user_id) && !this.admin_ids.contains(user_id)) {
            this.admin_ids.add(user_id);
        }
    }

    public void removeAdmin(int user_id) {
        this.admin_ids.remove(Integer.valueOf(user_id));
    }

    public void setGroupDescription(String group_description) {
        this.group_description = group_description;
    }

    public void setGroupIconUrl(String group_icon_url) {
        this.group_icon_url = group_icon_url;
    }
}
