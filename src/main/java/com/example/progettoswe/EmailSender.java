package com.example.progettoswe;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class EmailSender {

    public static void sendDeleteReservation(ArrayList<String> recipient, LocalDateTime time, String appointment, String isPT) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountMail = "derosandrew1999@gmail.com";
        String password = "zkmmgcyvhetnsjux";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountMail, password);
            }
        });

        for(int i=0;i<recipient.size();i++) {
            Message message = prepareMessageCancellation(session, myAccountMail, recipient.get(i), time, appointment, isPT);
            assert message != null;
            Transport.send(message);
        }
    }

    private static Message prepareMessageCancellation(Session session, String myAccountMail, String recipient, LocalDateTime time, String appointment, String isPT) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Prenotazione cancellata");
            if(isPT.equals(""))
            message.setText("La tua prenotazione del " + time.getDayOfMonth() +"-"+time.getMonthValue()+"-"+time.getYear()
                    +" alle "+time.getHour()+":00 : "+ appointment +" è stata cancellata.");
            else message.setText("La tua prenotazione del " + time.getDayOfMonth() +"-"+time.getMonthValue()+"-"+time.getYear()
                    +" alle "+time.getHour()+":00 : "+ appointment +" è stata cancellata.\n Causa: Assenza Personal Trainer");
            return message;
        } catch(Exception ex){
            System.out.println("errore");
        }
        return null;
    }

    public static void sendCredentials(String username, String pwd, String email) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountMail = "derosandrew1999@gmail.com";
        String password = "zkmmgcyvhetnsjux";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountMail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountMail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Creazione Account");
        message.setText("Il tuo username: " +  username + " \nLa tua password: "+pwd);
        Transport.send(message);
    }

    public static void sendReservationConfirm(String recipient, LocalDateTime time, String appointment) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountMail = "derosandrew1999@gmail.com";
        String password = "zkmmgcyvhetnsjux";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountMail, password);
            }
        });

        Message message = prepareReservationMessage(session, myAccountMail, recipient, time, appointment);
        assert message != null;
        Transport.send(message);
    }
    private static Message prepareReservationMessage(Session session, String myAccountMail, String recipient, LocalDateTime time, String appointment) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Prenotazione confermata");
            message.setText("La tua prenotazione del " + time.getDayOfMonth() +"-"+time.getMonthValue()+"-"+time.getYear()
                    +" alle "+time.getHour()+":00 : "+ appointment +" è confermata.");
            return message;
        } catch(Exception ex){
            System.out.println("errore");
        }
        return null;
    }
}