package com.progettoswe.model;


import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.DAO.UsersDAO;
import com.example.progettoswe.Model.UsersManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class UsersManagerTest {
    UsersManager userManager;
    UsersDAO userDAO;
    ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        try {
            userManager = new UsersManager();
            userDAO = new UsersDAO();
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
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void addUserTest() {
        try {
            userManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from utente where username = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals("password", rs.getString("password"));
            Assertions.assertEquals("seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(2, rs.getInt("tipo"));
            statement.close();
            Assertions.assertNotNull(userManager.getUser("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getUserPasswordTest() {
        try{
            userManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Assertions.assertEquals("password", userManager.getUserPassword("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getUserEmailTest() {
        try{
            userManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Assertions.assertEquals("seyevah501@asoflex.com", userManager.getUserEmail("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void existingUserTest() {
        try{
            userManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Assertions.assertTrue(userManager.existingUser("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getUserTypeTest() {
        try{
            userManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Assertions.assertEquals(2, userManager.getUserType("user_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void addPTTest() {
        try {
            userManager.addPersonalTrainer("pt_test","password","seyevah501@asoflex.com",3);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from PersonalTrainer where username = 'pt_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals("password", rs.getString("password"));
            Assertions.assertEquals("seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(3, rs.getInt("tipo"));
            statement.close();
            Assertions.assertNotNull(userManager.getPT("pt_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getPTPasswordTest() {
        try{
            userManager.addPersonalTrainer("pt_test","password","seyevah501@asoflex.com",3);
            Assertions.assertEquals("password", userManager.getPersonalTrainerPassword("pt_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void existingPTTest() {
        try{
            userManager.addPersonalTrainer("pt_test","password","seyevah501@asoflex.com",3);
            Assertions.assertTrue(userManager.existingPersonalTrainer("pt_test"));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


}