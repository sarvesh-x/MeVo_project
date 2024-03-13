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

    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    public void setUserAge(int UserAge){
        this.UserAge = UserAge;
    }
    public void setUserGender(String UserGender){
        this.UserGender = UserGender;
    }

}