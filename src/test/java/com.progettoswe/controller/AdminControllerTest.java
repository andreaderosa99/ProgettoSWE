package com.progettoswe.controller;

import com.example.progettoswe.Controller.AdminController;
import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.Model.UsersManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.*;


class AdminControllerTest {
    AdminController adminController;
    UsersManager usersManager;
    ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        try {
            usersManager = new UsersManager();
            connectionManager = ConnectionManager.getConnectionManager();
            adminController = new AdminController(usersManager);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "delete from Utente where username = 'user_test'";
            statement.executeUpdate(sql);
            sql = "delete from PersonalTrainer where username = 'pt_test'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void addUserTest() {
        try{
            adminController.addUser("user_test","seyevah501@asoflex.com",2);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from Utente where username = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals( "seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(2, rs.getInt("tipo"));
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void addPTTest() {
        try{
            adminController.addUser("pt_test","seyevah501@asoflex.com",3);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from PersonalTrainer where username = 'pt_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals( "seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(3, rs.getInt("tipo"));
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getUserTypeTest() {
        try{
            adminController.addUser("user_test","seyevah501@asoflex.com",2);
            Assertions.assertEquals(2,adminController.getUserType("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}