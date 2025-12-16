package com.chat.user.models;

public class User{
    public int userid;
    public String username;
    public String phone_no;
    public String profile_url;
    public String end_to_end_Key;
    public String created_at;
    public String bio;

    public User(int userid, String username, String phone_no, String profile_url, String end_to_end_Key, String created_at, String bio) {
        this.userid = userid;
        this.username = username;
        this.phone_no = phone_no;
        this.profile_url = profile_url;
        this.end_to_end_Key = end_to_end_Key;
        this.created_at = created_at;
        this.bio = bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void updatePublicKey(String end_to_end_Key) {
        this.end_to_end_Key = end_to_end_Key;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPublicKey() {
        return this.end_to_end_Key;
    }

    public int getUserId() {
        return this.userid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPhone_no() {
        return this.phone_no;
    }

    public String getProfile_url() {
        return this.profile_url;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getBio() {
        return this.bio;
    }
}

