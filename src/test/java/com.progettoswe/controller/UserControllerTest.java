package com.progettoswe.controller;

import com.example.progettoswe.Controller.UserController;
import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.Model.AppointmentsManager;
import com.example.progettoswe.Model.UsersManager;
import org.junit.jupiter.api.*;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


class UserControllerTest {
    UserController userController;
    ConnectionManager connectionManager;
    AppointmentsManager appointmentsManager;
    UsersManager userManager;

    @BeforeEach
    void setUp(){
        try {
            appointmentsManager = new AppointmentsManager();
            userManager = new UsersManager();
            connectionManager = ConnectionManager.getConnectionManager();
            userController = new UserController(appointmentsManager,userManager);
            userManager.addUser("user_test","password", "seyevah501@asoflex.com",2);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    @AfterEach
    void tearDown(){
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
    void getAvailableAppointmentsTest() {
        ArrayList<String> availableAppointments = userController.getAvailableAppointments(LocalDateTime.of(2023,3,19,8,0));
        Assertions.assertEquals(1, availableAppointments.size());
        Assertions.assertTrue(availableAppointments.contains("Corso Pilates"));
    }

    @Test
    void bookAppointmentTest() {
        try {
            userController.bookAppointment("user_test", LocalDateTime.of(2023, 3, 19, 8, 0));
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from Prenotazione where utente = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertTrue(rs.next());
            Assertions.assertEquals(Timestamp.valueOf(LocalDateTime.of(2023, 3, 19, 8, 0)), rs.getTimestamp("data_ora"));
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void deleteReservationTest() {
        try{
            userController.bookAppointment("user_test", LocalDateTime.of(2023, 3, 19, 8, 0));
            userController.deleteReservation(LocalDateTime.of(2023, 3, 19, 8, 0),"user_test");
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from Prenotazione where utente = 'user_test'";
            ResultSet rs = statement.executeQuery(sql);
            Assertions.assertFalse(rs.next());
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}