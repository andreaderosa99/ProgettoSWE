package com.example.progettoswe.Model;

public class User {
    private String username;
    private String password;
    private String email;
    int type;

    public User(String username, String password, String email, int type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type=type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getType(){return type;}

}
