package com.example.progettoswe.Controller;

import com.example.progettoswe.Model.UsersManager;

public class LoginController {
    UsersManager usersManager;

    public LoginController(UsersManager userManager) {
        this.usersManager = userManager;
    }

    public boolean isAnExistingUser(String username){
        return usersManager.existingUser(username);
    }

    public String getUserPassword(String username){
        return usersManager.getUserPassword(username);
    }

    public boolean isAnExsistingPersonalTrainer(String username){
        return usersManager.existingPersonalTrainer(username);
    }

    public String getPersonalTrainerPassword(String username){
        return usersManager.getPersonalTrainerPassword(username);
    }


}

