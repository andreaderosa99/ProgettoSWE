package com.progettoswe.model;


import com.example.progettoswe.Model.Booking;
import org.junit.jupiter.api.*;

class BookingTest {
    static Booking booking;

    @BeforeAll
    static void setUp() {
        booking = new Booking();
    }

    @Test
    void addAppointmentBookedTest() {
        booking.addAppointmentBooked("corso_test", "user_test");
        Assertions.assertTrue(booking.getAppointmentsBooked().containsKey("corso_test"));
        Assertions.assertEquals("user_test", booking.getAppointmentsBooked().get("corso_test"));
    }

    @Test
    void removeAppointmentBookedTest() {
        booking.addAppointmentBooked("corso_test", "user_test");
        booking.removeAppointmentBooked("corso_test", "user_test");
        Assertions.assertFalse(booking.getAppointmentsBooked().containsKey("corso_test"));
    }
}