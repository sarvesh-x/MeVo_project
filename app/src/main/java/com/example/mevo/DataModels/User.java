package com.example.mevo.DataModels;

public class User {
    private String UserName;
    private int UserAge;
    private String UserGender;

    public User(String UserName, int UserAge, String UserGender) {
        this.UserName = UserName;
        this.UserAge = UserAge;
        this.UserGender = UserGender;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserGender() {
        return UserGender;
    }

    public int getUserAge() {
        return UserAge;
    }
}