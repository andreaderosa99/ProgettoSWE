package com.progettoswe.dao;


import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.DAO.ModelDAO;
import com.example.progettoswe.DAO.UsersDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;


class ModelDAOTest {
    static ConnectionManager connectionManager;
    static ModelDAO modelDAO;
    static UsersDAO userDAO;


    @BeforeEach
    void setUp() {
        try {
            connectionManager = ConnectionManager.getConnectionManager();
            modelDAO = new ModelDAO();
            userDAO = new UsersDAO();
            userDAO.addUser("user_test","password","kkvnwhite@gmail.com",2);
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addReservationTest() {
        try {
            modelDAO.addReservation(LocalDateTime.of(2023,1,19,8,0),
                    "Corso Pilates", "user_test","seyevah501@asoflex.com");
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from Prenotazione where utente = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals(Timestamp.valueOf(LocalDateTime.of(2023, 1, 19, 8, 0)),
                    rs.getTimestamp("data_ora"));
            Assertions.assertEquals( "Corso Pilates", rs.getString("appuntamento"));
            statement.close();
        } catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void removeReservationPTTest() {
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "insert into Prenotazione values('Corso Pilates' , '"+ Timestamp.valueOf(LocalDateTime.of(2023,1,19,8,0))+ "', 'user_test')";
            statement.executeUpdate(sql);
            modelDAO.removeReservationPT(LocalDateTime.of(2023,1,19,8,0), "mario_pt");
            sql = "select * from Prenotazione where utente = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertFalse(rs.next());//viene controllata se la tupla inserita è stata rimossa
            statement.close();
        } catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void removeReservationUserTest() {
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "insert into Prenotazione values('Corso Pilates' , '"+ Timestamp.valueOf(LocalDateTime.of(2023,1,19,8,0))+ "', 'user_test')";
            statement.executeUpdate(sql);
            modelDAO.removeReservationUser(LocalDateTime.of(2023,1,19,8,0), "user_test");
            sql = "select * from Prenotazione where utente = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertFalse(rs.next());
            statement.close();
        } catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}