package com.example.progettoswe.Controller;

import java.sql.SQLException;
import com.example.progettoswe.Model.*;
import javax.mail.MessagingException;

public class AdminController {
    private final UsersManager usersManager;

    public AdminController(UsersManager usersManager) throws SQLException {
        this.usersManager = usersManager;
    }

    public String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public boolean addUser(String username, String email, int type) throws SQLException, MessagingException {
        String password = getAlphaNumericString(8);
        if(type==2)
            return usersManager.addUser(username, password, email,type);
        else return usersManager.addPersonalTrainer(username,password,email,type);
    }

    public int getUserType(String username){
        return usersManager.getUserType(username);
    }
}