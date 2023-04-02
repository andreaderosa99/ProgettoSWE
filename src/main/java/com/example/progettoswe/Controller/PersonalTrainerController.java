package com.example.progettoswe.Controller;


import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.progettoswe.Model.*;
import com.example.progettoswe.EmailSender;

public class PersonalTrainerController{

    AppointmentsManager appointmentsManager;

    public PersonalTrainerController(AppointmentsManager appointmentsManager) throws SQLException {
        this.appointmentsManager = appointmentsManager;
    }

    public void deleteReservation(LocalDateTime time, String pt) throws SQLException, MessagingException {
        EmailSender.sendDeleteReservation(appointmentsManager.getEmails(pt), time, appointmentsManager.getAppPTtodelete(pt),"pt");
        appointmentsManager.deleteReservation(time,pt);
    }

    public ArrayList<String> getAvailableAppointments(String pt,LocalDateTime time) throws SQLException {
        return appointmentsManager.getAppointmentsList(pt,time);
    }
}

