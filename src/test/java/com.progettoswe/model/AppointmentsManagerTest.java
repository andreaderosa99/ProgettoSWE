package com.progettoswe.model;

import com.example.progettoswe.DAO.ConnectionManager;
import com.example.progettoswe.DAO.UsersDAO;
import com.example.progettoswe.Model.Booking;
import com.example.progettoswe.Model.AppointmentsManager;
import org.junit.jupiter.api.*;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;

class AppointmentsManagerTest {
    AppointmentsManager appointmentsManager;
    ConnectionManager connectionManager;
    UsersDAO usersDAO;

    @BeforeEach
    void setUp() {
        try {
            connectionManager = ConnectionManager.getConnectionManager();
            appointmentsManager = new AppointmentsManager();
            usersDAO = new UsersDAO();
            usersDAO.addUser("user_test","password", "seyevah501@asoflex.com",2);
            usersDAO.addPersonalTrainer("pt_test","password","seyevah501@asoflex.com",3);
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
            sql = "delete from PersonalTrainer where username = 'pt_test'";
            statement.executeUpdate(sql);
            statement.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addReservationTest() {
        try{
            Booking booking1 = appointmentsManager.pullBookings(LocalDateTime.of(2023,1,19,8,0));
            if(!booking1.getAppointmentsBooked().containsKey("Corso Pilates")) {
                appointmentsManager.addReservation(LocalDateTime.of(2023, 1, 19, 8, 0), "Corso Pilates", "user_test","seyevah501@asoflex.com");
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                String sql = "select * from Prenotazione where utente = 'user_test'";
                ResultSet rs = statement.executeQuery(sql);
                Assertions.assertTrue(rs.next());
                Assertions.assertEquals(Timestamp.valueOf(LocalDateTime.of(2023, 1, 19, 8, 0)), rs.getTimestamp("data_ora"));
                Assertions.assertEquals( "Corso Pilates", rs.getString("appuntamento"));
                statement.close();
                Booking booking2 = appointmentsManager.pullBookings(LocalDateTime.of(2023, 1, 19, 8, 0));
                Assertions.assertTrue(booking2.getAppointmentsBooked().containsKey("Corso Pilates"));
                Assertions.assertEquals("user_test", booking2.getAppointmentsBooked().get("Corso Pilates"));
            }
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } catch (MessagingException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    @Test
    void deleteReservationUserTest() {
        try{
            Booking booking1 = appointmentsManager.pullBookings(LocalDateTime.of(2023,1,19,8,0));
            if(!booking1.getAppointmentsBooked().containsKey("Corso Pilates")) {
                appointmentsManager.addReservation(LocalDateTime.of(2023, 1, 19, 8, 0), "Corso Pilates", "user_test","seyevah501@asoflex.com");
                appointmentsManager.deleteReservationUser(LocalDateTime.of(2023, 1, 19, 8, 0),"user_test");
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                String sql = "select * from Prenotazione where utente = 'user_test'";
                ResultSet rs = statement.executeQuery(sql);
                Assertions.assertFalse(rs.next());
                statement.close();
                Booking booking = appointmentsManager.pullBookings(LocalDateTime.of(2023, 1, 19, 8, 0));
                Assertions.assertFalse(booking.getAppointmentsBooked().containsKey("Corso Pilates"));
            }
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } catch (MessagingException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    @Test
    void deleteReservationTest() {
        try{
            Booking booking1 = appointmentsManager.pullBookings(LocalDateTime.of(2023,1,19,8,0));
            if(!booking1.getAppointmentsBooked().containsKey("Corso Pilates")) {
                appointmentsManager.addReservation(LocalDateTime.of(2023, 1, 19, 8, 0), "Corso Pilates", "user_test","seyevah501@asoflex.com");
                appointmentsManager.deleteReservation(LocalDateTime.of(2023, 1, 19, 8, 0),"pt_test");
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                String sql = "select * from Prenotazione where utente = 'user_test'";
                ResultSet rs = statement.executeQuery(sql);
                Assertions.assertFalse(rs.next());
                statement.close();
                Booking booking = appointmentsManager.pullBookings(LocalDateTime.of(2023, 1, 19, 8, 0));
                Assertions.assertFalse(booking.getAppointmentsBooked().containsKey("Corso Pilates"));
            }
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } catch (MessagingException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
}