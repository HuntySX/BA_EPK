package com.company.Print.EventDriven;

public class Print_User {
    private Integer User_ID;
    private String name;
    private String Lastname;

    public Print_User(Integer user_ID, String name, String lastname) {
        User_ID = user_ID;
        this.name = name;
        Lastname = lastname;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }
}
