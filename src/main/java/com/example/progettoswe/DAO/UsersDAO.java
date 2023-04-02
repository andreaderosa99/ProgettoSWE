package com.example.progettoswe.DAO;


import java.sql.*;
import java.util.ArrayList;
import com.example.progettoswe.Model.*;

public class UsersDAO {
    ConnectionManager connectionManager;
    Connection connection;

    public UsersDAO() throws SQLException{
        this.connectionManager = ConnectionManager.getConnectionManager();
    }

    public ArrayList<User> pullUsers() throws SQLException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from Utente";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<User> users = new ArrayList<>();
        while(rs.next()){
            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            int type = rs.getInt("tipo");
            users.add(new User(username, password, email,type));
        }
        statement.close();
        return users;
    }

    public ArrayList<PersonalTrainer> pullPersonalTrainers() throws SQLException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from PersonalTrainer";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<PersonalTrainer> pt = new ArrayList<>();
        while(rs.next()){
            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            int type = rs.getInt("tipo");
            pt.add(new PersonalTrainer(username, password, email,type));
        }
        statement.close();
        return pt;
    }

    public void addUser(String username, String password,String email,int type) throws SQLException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into Utente values('"+ username + "','" + password + "','" + email + "','"+ type +"')";
        statement.executeUpdate(sql);
        statement.close();
    }
    public void addPersonalTrainer(String username, String password, String email,int type) throws SQLException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into PersonalTrainer values('"+ username + "','" + password + "','"+ email +"','"+type+"')";
        statement.executeUpdate(sql);
        statement.close();
    }

}