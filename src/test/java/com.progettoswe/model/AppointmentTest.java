package com.progettoswe.model;

import com.example.progettoswe.Model.Appointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AppointmentTest {
    static Appointment appointment;

    @BeforeAll
    static void setUp() {
        appointment = new Appointment();
    }

    @Test
    void addAppointmentTest() {
        appointment.addAppointment("corso_test","pt_test");
        Assertions.assertTrue(appointment.getAppointment().containsKey("corso_test"));
    }

}

