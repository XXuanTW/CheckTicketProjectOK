package com.example.user.checkticketproject;

public class Json {
    int id;
    int age;
    int ticket;
    int original;
    String checkid;
    String phone;
    String username;
    String time;

    public Json(int id, int age, int ticket, int original, String time, String checkid, String phone) {
        this.id = id;
        this.age = age;
        this.ticket = ticket;
        this.original = original;
        this.checkid = checkid;
        this.phone = phone;
        this.username = username;
        this.time = this.time;
    }

    public Json(int id, int age, int ticket, int username, String time, String phone, String checkid, String original) {
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getTicket() {
        return ticket;
    }

    public int getOriginal() {
        return original;
    }

    public String getCheckid() {
        return checkid;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
