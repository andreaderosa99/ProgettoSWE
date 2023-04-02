package com.example.progettoswe.Model;

public class PersonalTrainer {
    private String username;
    private String password;
    private String email;
    private int type;

    public PersonalTrainer(String username, String password, String email, int type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public int getType(){return type;}

}
