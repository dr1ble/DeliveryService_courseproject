package com.example.deliveryservice_courseproject.Models;

public class User {
    private String login, password, id, accesslevel;

    public User(String id, String login, String password, String accesslevel) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.accesslevel = accesslevel;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }
}
