package com.example.progettoswe.DAO;

import com.example.progettoswe.EmailSender;
import com.example.progettoswe.Model.Appointment;
import com.example.progettoswe.Model.Booking;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelDAO {
    ConnectionManager connectionManager;
    Connection connection;

    public ModelDAO() throws SQLException{
        this.connectionManager = ConnectionManager.getConnectionManager();
    }


    public HashMap<LocalDateTime,Appointment> pullAppointments() throws SQLException {
        //prende gli appuntamenti dal database
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from Appuntamento";
        ResultSet rs = statement.executeQuery(sql);
        HashMap<LocalDateTime, Appointment> scheduledAppointments = new HashMap<>();
        while (rs.next()){
            String title = rs.getString("titolo");
            String pt = rs.getString("pt");
            LocalDateTime time = rs.getTimestamp("data").toLocalDateTime();
            if(!scheduledAppointments.containsKey(time)){
                Appointment appointment = new Appointment();
                appointment.addAppointment(title,pt);
                scheduledAppointments.put(time, appointment);
            } else {
                scheduledAppointments.get(time).addAppointment(title,pt);
            }
        }
        statement.close();
        return scheduledAppointments;
    }

    public HashMap<LocalDateTime, Booking> pullScheduledBookings() throws SQLException {
        //prende le prenotazioni dal database
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from Prenotazione order by data_ora";
        ResultSet rs = statement.executeQuery(sql);
        HashMap<LocalDateTime, Booking> scheduledAppointmentBooked = new HashMap<>();
        while (rs.next()) {
            String appointment = rs.getString("appuntamento");
            LocalDateTime time = rs.getTimestamp("data_ora").toLocalDateTime();
            String user = rs.getString("utente");
            if(!scheduledAppointmentBooked.containsKey(time)){
                Booking booking = new Booking();
                booking.addAppointmentBooked(appointment, user);
                scheduledAppointmentBooked.put(time, booking);
            } else {
                scheduledAppointmentBooked.get(time).addAppointmentBooked(appointment, user);
            }
        }
        statement.close();
        return scheduledAppointmentBooked;
    }

    public int addReservation(LocalDateTime time, String appointment, String username, String email)
            throws SQLException, MessagingException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();

        //prima controlla se c'è posto o se l'utente ha già prenotato
        if(checkCapacity(time) && checkDuplicate(time,username)) {
            String sql = "insert into Prenotazione values('" + appointment + "','" + Timestamp.valueOf(time) + "','" + username + "')";
            statement.executeUpdate(sql);
            EmailSender.sendReservationConfirm(email, time, appointment);
            return 0;
        }
        statement.close();
        return -1;
    }

    public void removeReservationPT(LocalDateTime time, String pt) throws SQLException {
        //rimuove le prenotazioni degli utenti una volta che il pt disdice il corso
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from Prenotazione where data_ora in (select data_ora from Appuntamento a,Prenotazione p where a.pt='"+ pt +"' and a.titolo=p.appuntamento and data_ora='"+ Timestamp.valueOf(time) +"')" ;
        statement.executeUpdate(sql);
        statement.close();
        removeAppointment(pt,time);//deve eliminare l'appuntamento per impedire altre prenotazioni
    }

    public void removeReservationUser(LocalDateTime time, String user) throws SQLException {
        //rimuove la prenotazione fatta dall'utente
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from Prenotazione where data_ora in (select data_ora from Appuntamento a,Prenotazione p where p.utente='"+ user +"' and a.titolo=p.appuntamento and data_ora='"+ Timestamp.valueOf(time) +"')" ;
        statement.executeUpdate(sql);
        statement.close();
    }

    public void removeAppointment(String pt,LocalDateTime time) throws SQLException {
        //dopo aver eliminato le prenotazioni degli utenti, bisogna impedire che altri utenti possano prenotarsi
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from Appuntamento where pt='"+pt+"' and data='"+Timestamp.valueOf(time)+"'";
        statement.executeUpdate(sql);
        statement.close();
    }

    public ArrayList<String> emailList(String pt) throws SQLException {
        //serve per prendere le email di chi era prenotato, in modo da comunicare l'assenza del pt
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select email from Prenotazione p,Appuntamento a,Utente u where p.appuntamento=a.titolo and p.utente=u.username and a.pt='"+pt+"'";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString("email"));
        }
        statement.close();
        return list;
    }

    public ArrayList<String> usernameList(String pt) throws SQLException {
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select u.username from Prenotazione p,Appuntamento a,Utente u where p.appuntamento=a.titolo and p.utente=u.username and a.pt='"+pt+"'";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString("username"));
        }
        statement.close();
        return list;
    }

    public String getAppointmentPTtodelete(String pt) throws SQLException {
        //eliminazione da parte del personal trainer
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select a.titolo from Prenotazione p,Appuntamento a,Utente u where p.appuntamento=a.titolo and p.utente=u.username and a.pt='"+pt+"'";
        ResultSet rs = statement.executeQuery(sql);
        String result="";
        while (rs.next()){
            result = rs.getString("titolo");
        }
        statement.close();
        return result;
    }

    public String getAppointmentUsertodelete(String user) throws SQLException {
        //eliminazione da parte dell'utente
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select a.titolo from Prenotazione p,Appuntamento a,Utente u where p.appuntamento=a.titolo and p.utente=u.username and p.utente='"+user+"'";
        ResultSet rs = statement.executeQuery(sql);
        String result="";
        while (rs.next()){
            result = rs.getString("titolo");
        }
        statement.close();
        return result;
    }

    public ArrayList<String> appointmentLists(String pt, LocalDateTime time) throws SQLException {
        //serve al pt per controllare se ha appuntamenti per una data specifica
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select a.titolo from Appuntamento a where a.pt='"+pt+"' and a.data='"+Timestamp.valueOf(time)+"'";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> result=new ArrayList<>();
        while (rs.next()){
            result.add(rs.getString("titolo"));
        }
        statement.close();
        return result;
    }

    public boolean checkCapacity(LocalDateTime time) throws SQLException{
        //controlla se c'è posto
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select count(*) as numero from Prenotazione where data_ora='"+ Timestamp.valueOf(time)+"'";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            int count = rs.getInt("numero");
            if(count>20){
                statement.close();
                return false;
            }
        }
        statement.close();
        return true;
    }

    public boolean checkDuplicate(LocalDateTime time, String username) throws SQLException{
        //controlla se non è stata già effettuata una prenotazione dall'utente
        connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select count(*) as numero from Prenotazione where data_ora='"+ Timestamp.valueOf(time)+"' and utente = '" + username +"'";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            int count = rs.getInt("numero");
            if(count>0){
                statement.close();
                return false;
            }
        }
        statement.close();
        return true;
    }

}
