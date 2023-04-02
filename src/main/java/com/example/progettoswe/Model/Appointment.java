package com.example.progettoswe.Model;


import java.util.ArrayList;
import java.util.HashMap;

public class Appointment {
    private final HashMap<String, String> appointment;

    public Appointment() {
        this.appointment = new HashMap<String, String>();
    }

    public void addAppointment(String name, String pt) {
        if (!appointment.containsKey(name)) {
            appointment.put(name, pt);
        }
    }

    public HashMap<String, String> getAppointment() {
        return appointment;
    }
}
