package com.example.proyectonavigation.model;

import java.util.Date;

public class User {

    private int id;
    private String username;
    private String email;
    private Date birthday;
    private String password;
    private int picture;
    private String preferences;

    public User(int id, String username, String email, Date birthday, String password, int picture, String preferences) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.picture = picture;
        this.preferences = preferences;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", picture=" + picture +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
