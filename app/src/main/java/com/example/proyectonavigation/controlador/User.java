package com.example.proyectonavigation.controlador;

import java.util.Date;

public class User {

    private int id;
    private String username;
    private String email;
    private Date birthday;
    private String password;
    private int picture;
    private String preference_1;
    private String preference_2;
    private String preference_3;

    public User(int id, String username, String email, Date birthday, String password, int picture, String preference_1, String preference_2, String preference_3) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.picture = picture;
        this.preference_1 = preference_1;
        this.preference_2 = preference_2;
        this.preference_3 = preference_3;
    }

    public User(String preference_1, String preference_2, String preference_3) {
        this.preference_1 = preference_1;
        this.preference_2 = preference_2;
        this.preference_3 = preference_3;
    }

    public User() {
    }

    public String getPreference_1() {
        return preference_1;
    }

    public void setPreference_1(String preference_1) {
        this.preference_1 = preference_1;
    }

    public String getPreference_2() {
        return preference_2;
    }

    public void setPreference_2(String preference_2) {
        this.preference_2 = preference_2;
    }

    public String getPreference_3() {
        return preference_3;
    }

    public void setPreference_3(String preference_3) {
        this.preference_3 = preference_3;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", picture=" + picture +
                ", preference_1='" + preference_1 + '\'' +
                ", preference_2='" + preference_2 + '\'' +
                ", preference_3='" + preference_3 + '\'' +
                '}';
    }
}
