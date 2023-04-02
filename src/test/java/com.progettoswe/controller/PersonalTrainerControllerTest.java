package com.progettoswe.controller;

import com.example.progettoswe.Controller.PersonalTrainerController;
import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.Model.AppointmentsManager;
import com.example.progettoswe.Model.Booking;
import com.example.progettoswe.Model.UsersManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


class PersonalTrainerControllerTest {
    PersonalTrainerController personalTrainerController;
    AppointmentsManager appointmentsManager;
    UsersManager usersManager;
    ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        try {
            usersManager = new UsersManager();
            appointmentsManager = new AppointmentsManager();
            connectionManager = ConnectionManager.getConnectionManager();
            personalTrainerController = new PersonalTrainerController(appointmentsManager);
            usersManager.addPersonalTrainer("pt_test", "password", "seyevah501@asoflex.com", 3);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "delete from PersonalTrainer where username = 'pt_test'";
            statement.executeUpdate(sql);
            sql = "delete from Utente where username = 'user_test'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void deleteReservationPTTest() {
        try{
            usersManager.addUser("user_test","password","seyevah501@asoflex.com",2);
            Booking booking1 = appointmentsManager.pullBookings(LocalDateTime.of(2023,3,19,8,0));
            if(!booking1.getAppointmentsBooked().containsKey("Corso Pilates")) {
                appointmentsManager.addReservation(LocalDateTime.of(2023, 3, 19, 8, 0), "Corso Pilates", "user_test","seyevah501@asoflex.com");
                try {
                    personalTrainerController.deleteReservation(LocalDateTime.of(2023, 3, 19, 8, 0),"mario_pt");
                }catch(Exception e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                String sql = "select * from Prenotazione where utente = 'user_test'";
                ResultSet rs = statement.executeQuery(sql);
                Assertions.assertFalse(rs.next());
                ArrayList<String> availableAppointments = personalTrainerController.getAvailableAppointments("mario_pt",LocalDateTime.of(2023,3,19,8,0));
                Assertions.assertFalse(availableAppointments.contains("Corso Pilates"));
                sql = "insert into Appuntamento values('Corso Pilates','mario_pt','2023-03-19 08:00:00.0')";
                statement.executeUpdate(sql);
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    void getAvailableAppointments() throws SQLException {
        try {
            ArrayList<String> availableAppointments = personalTrainerController.getAvailableAppointments("mario_pt", LocalDateTime.of(2023, 3, 19, 8, 0));
            Assertions.assertEquals(1, availableAppointments.size());
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


}