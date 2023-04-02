package com.example.progettoswe.Model;

import java.util.HashMap;

public class Booking {
    private final HashMap<String, String> appointmentsBooked;

    public Booking() {
        appointmentsBooked = new HashMap<>();
    }

    public void addAppointmentBooked(String appointment, String username){
        appointmentsBooked.put(appointment, username);
    }

    public void removeAppointmentBooked(String appointment, String username){
        appointmentsBooked.remove(appointment, username);
    }

    public HashMap<String, String> getAppointmentsBooked() {
        return appointmentsBooked;
    }

}
