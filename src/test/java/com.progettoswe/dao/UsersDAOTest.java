package com.progettoswe.dao;

import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.DAO.UsersDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class UsersDAOTest {
    static ConnectionManager connectionManager;
    static UsersDAO userDAO;

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
        try {
            userDAO = new UsersDAO();
            connectionManager = ConnectionManager.getConnectionManager();
            userDAO.addUser("user_test","password", "seyevah501@asoflex.com",2);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from Utente where username = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals("password", rs.getString("password"));
            Assertions.assertEquals("seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(2 , rs.getInt("tipo"));
            sql = "delete from Utente where username = 'user_test'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void addPTTest() {
        try {
            userDAO = new UsersDAO();
            connectionManager = ConnectionManager.getConnectionManager();
            userDAO.addPersonalTrainer("pt_test","password", "seyevah501@asoflex.com",3);
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from PersonalTrainer where username = 'pt_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals("password", rs.getString("password"));
            Assertions.assertEquals("seyevah501@asoflex.com", rs.getString("email"));
            Assertions.assertEquals(3 , rs.getInt("tipo"));
            sql = "delete from PersonalTrainer where username = 'pt_test'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}