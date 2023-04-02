package com.example.progettoswe.Model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.progettoswe.DAO.ModelDAO;

import javax.mail.MessagingException;

public class AppointmentsManager {
    private final ModelDAO modelDAO;
    private final HashMap<LocalDateTime,Appointment> appointments;
    private final HashMap<LocalDateTime, Booking> scheduledAppointmentBooked;

    public AppointmentsManager() throws SQLException {
        modelDAO = new ModelDAO();
        appointments = modelDAO.pullAppointments();
        scheduledAppointmentBooked = modelDAO.pullScheduledBookings();
    }

    public Booking pullBookings(LocalDateTime time){
        if (!scheduledAppointmentBooked.containsKey(time)){
            Booking booking = new Booking();
            scheduledAppointmentBooked.put(time, booking);
        }
        return  scheduledAppointmentBooked.get(time);
    }

    public HashMap<LocalDateTime,Appointment> getAppointments(){
        return appointments;
    }

    public int addReservation(LocalDateTime time, String appointment, String username,String email) throws SQLException, MessagingException {
        scheduledAppointmentBooked.get(time).addAppointmentBooked(appointment,username);
        return modelDAO.addReservation(time, appointment, username,email);
    }

    public void deleteReservation(LocalDateTime time, String pt) throws SQLException {
        String app=getAppPTtodelete(pt);
        ArrayList<String> uss=getUsernames(pt);
        //prende la lista di tutti gli utenti prenotati a quello specifico corso
        //che serve per andare a rimuovere le prenotazioni

        for(int i=0;i<uss.size();i++)
        scheduledAppointmentBooked.get(time).removeAppointmentBooked(app, uss.get(i));

        modelDAO.removeReservationPT(time, pt);
    }

    public void deleteReservationUser(LocalDateTime time, String user) throws SQLException {
        String app = getAppUsertodelete(user);
        scheduledAppointmentBooked.get(time).removeAppointmentBooked(app, user);
        modelDAO.removeReservationUser(time, user);
    }

    public ArrayList<String> getEmails(String pt) throws SQLException {
        return modelDAO.emailList(pt);
    }

    public ArrayList<String> getUsernames(String pt) throws SQLException {
        return modelDAO.usernameList(pt);
    }

    public String getAppPTtodelete(String pt) throws SQLException {
        //metodo per eliminare la prenotazione, il metodo viene usato da personal trainer
        return modelDAO.getAppointmentPTtodelete(pt);
    }

    public String getAppUsertodelete(String user) throws SQLException {
        //serve per eliminare la prenotazione, il metodo viene usato da utente
        return modelDAO.getAppointmentUsertodelete(user);
    }

    public ArrayList<String> getAppointmentsList(String pt, LocalDateTime time) throws SQLException {
        //metodo per ottenere gli appuntamenti associati al personal trainer in una specifica data
        return modelDAO.appointmentLists(pt,time);
    }
    
}