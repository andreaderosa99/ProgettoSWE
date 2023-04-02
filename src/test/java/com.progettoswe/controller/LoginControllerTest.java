package com.progettoswe.controller;

import com.example.progettoswe.Controller.AdminController;
import com.example.progettoswe.Controller.LoginController;
import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.Model.UsersManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class LoginControllerTest {
    LoginController loginController;
    AdminController adminController;
    UsersManager usersManager;
    ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        try {
            usersManager = new UsersManager();
            adminController = new AdminController(usersManager);
            loginController = new LoginController(usersManager);
            connectionManager = ConnectionManager.getConnectionManager();
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
    void isAnExistingUserTest() {
        try {
            adminController.addUser("user_test","seyevah501@asoflex.com", 2);
            Assertions.assertTrue(loginController.isAnExistingUser("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getUserPasswordTest() {
        try {
            adminController.addUser("user_test", "seyevah501@asoflex.com", 2);
            Assertions.assertEquals(usersManager.getUser("user_test").getPassword(), loginController.getUserPassword("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void isAnExistingPTTest() {
        try {
            adminController.addUser("pt_test", "seyevah501@asoflex.com", 3);
            Assertions.assertTrue(loginController.isAnExsistingPersonalTrainer("pt_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getPTPasswordTest() {
        try {
            adminController.addUser("pt_test", "seyevah501@asoflex.com",3);
            Assertions.assertEquals(usersManager.getPT("pt_test").getPassword(), loginController.getPersonalTrainerPassword("pt_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
}