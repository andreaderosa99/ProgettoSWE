package com.example.progettoswe.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.example.progettoswe.DAO.UsersDAO;
import com.example.progettoswe.EmailSender;

import javax.mail.MessagingException;

public class UsersManager {
    private final ArrayList<User> users;
    private final ArrayList<PersonalTrainer> pts;
    private final UsersDAO usersDAO;

    public UsersManager() throws SQLException {
        usersDAO =  new UsersDAO();
        users = usersDAO.pullUsers();
        pts = usersDAO.pullPersonalTrainers();
    }

    public String getUserPassword(String username){
        for (User user : users){
            if(Objects.equals(user.getUsername(), username))
                return user.getPassword();
        }
        return null;
    }

    public String getUserEmail(String username){
        for (User user : users){
            if(Objects.equals(user.getUsername(), username))
                return user.getEmail();
        }
        return null;
    }

    public String getPersonalTrainerPassword(String username){
        for (PersonalTrainer personalTrainer : pts){
            if(Objects.equals(personalTrainer.getUsername(), username))
                return personalTrainer.getPassword();
        }
        return null;
    }

    public boolean existingUser(String username){
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return true;
            }
        }
        return false;
    }

    public boolean existingPersonalTrainer(String username){
        for (PersonalTrainer personalTrainer : pts) {
            if (Objects.equals(personalTrainer.getUsername(), username)) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String username, String password, String email, int type) throws SQLException, MessagingException {
        if(!existingUser(username)) {
            User user = new User(username, password, email,type);
            users.add(user);
            usersDAO.addUser(username, password, email,type);
            EmailSender.sendCredentials(username,password,email);
            return true;
        }
        return false;
    }

    public User getUser(String username){
        for(User user : users){
            if(Objects.equals(user.getUsername(), username))
                return user;
        }
        return null;
    }

    public PersonalTrainer getPT(String username){
        for(PersonalTrainer pt : pts){
            if(Objects.equals(pt.getUsername(), username))
                return pt;
        }
        return null;
    }

    public int getUserType(String username){

        for(User user : users){
            if(Objects.equals(user.getUsername(), username))
                return user.getType();
        }

        for(PersonalTrainer personalTrainer : pts){
            if(Objects.equals(personalTrainer.getUsername(), username))
                return personalTrainer.getType();
        }

        return 0;
    }

    public boolean addPersonalTrainer(String username, String password, String email,int type) throws SQLException, MessagingException {
        if(!existingPersonalTrainer(username)) {
            PersonalTrainer PersonalTrainer = new PersonalTrainer(username, password, email,type);
            pts.add(PersonalTrainer);
            usersDAO.addPersonalTrainer(username, password, email,type);
            EmailSender.sendCredentials(username,password,email);
            return true;
        }
        return false;
    }

}