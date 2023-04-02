package com.example.progettoswe.Controller;


import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.progettoswe.EmailSender;
import com.example.progettoswe.Model.*;

public class UserController {

    AppointmentsManager appointmentsManager;
    UsersManager usersManager;
    HashMap<LocalDateTime,Appointment> appointment;
    Booking bookings;

    public UserController(AppointmentsManager appointmentsManager, UsersManager usersManager) throws SQLException {
        this.appointmentsManager = appointmentsManager;
        this.usersManager = usersManager;
    }

    public ArrayList<String> getAvailableAppointments(LocalDateTime time){
        bookings = appointmentsManager.pullBookings(time);
        appointment = appointmentsManager.getAppointments();
        ArrayList<String> appointments = new ArrayList<>();

        appointments.addAll(appointment.get(time).getAppointment().keySet());

        return appointments;
    }

    public String bookAppointment(String username, LocalDateTime time) throws SQLException, MessagingException {
        User user = usersManager.getUser(username);
        ArrayList<String> appointments = getAvailableAppointments(time);

        if(!appointments.isEmpty()){
            String tmp = appointments.get(0);
            int var = appointmentsManager.addReservation(time, tmp, user.getUsername(),user.getEmail());
            if(var==0)
                return tmp;
            else return null;
        }
        return null;
    }

    public void deleteReservation(LocalDateTime time, String user) throws SQLException, MessagingException {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(usersManager.getUserEmail(user));
        EmailSender.sendDeleteReservation(tmp, time, appointmentsManager.getAppUsertodelete(user),"");
        appointmentsManager.deleteReservationUser(time,user);
    }

}
