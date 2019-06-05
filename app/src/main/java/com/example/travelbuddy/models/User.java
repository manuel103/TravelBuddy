package com.example.travelbuddy.models;

public class User {

    private int id;
    private String email, user_name;

    public User(int id, String email, String user_name) {
        this.id = id;
        this.email = email;
        this.user_name = user_name;
    }

    public int getId() {

        return id;
    }

    public String getEmail() {

        return email;
    }

    public String getName() {
        return user_name;
    }

}
