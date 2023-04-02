package com.example.progettoswe;

import com.example.progettoswe.Controller.*;
import com.example.progettoswe.Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class InterfaceController implements Initializable {
    public Button signUpButton;
    public Button CheckReservation;
    public Button Book;
    public RadioButton UserButton;
    public RadioButton PersonalTrainerButton;
    ObservableList<String> hours = FXCollections.observableArrayList("8:00","10:00","14:00","16:00",
            "18:00");
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static LocalDateTime dateTime;
    private static String currentUser;
    private static ArrayList<String> availableappointments;
    private LoginController loginController;
    private UserController userController;
    private PersonalTrainerController PersonalTrainerController;
    private AdminController adminController;

    @FXML
    private Label inexistingUserLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private ToggleGroup ruolo;

    @FXML
    private TextField usernameField2;
    @FXML
    private TextField emailField2;
    @FXML
    private Label signUpSuccesful;

    @FXML
    private Label DeleteLabel;

    @FXML
    private ChoiceBox<String> chooseHour = new ChoiceBox<>();

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label BookingLabel;

    @FXML
    private ChoiceBox<String> chooseHourPT = new ChoiceBox<>();

    @FXML
    private ListView<String> AvailableappointmentListPT;

    @FXML
    private DatePicker datePickerPT;
    @FXML
    private Button deleteUserReservation;
    @FXML
    private Label PTlabel;

    @FXML
    void CheckReservation(MouseEvent event) throws SQLException {
        LocalDate date = datePickerPT.getValue();
        if(date != null) {
            AvailableappointmentListPT.getItems().clear();
            LocalTime time = getTime(chooseHourPT.getSelectionModel().getSelectedItem());
            dateTime = LocalDateTime.of(date,time);
            availableappointments = PersonalTrainerController.getAvailableAppointments(currentUser,dateTime);
            AvailableappointmentListPT.getItems().addAll(availableappointments);
        } else{
            PTlabel.setText("Seleziona una data prima di prenotare!");
            PTlabel.setTextFill(Color.web("#ff0000"));
        }

    }
    @FXML
    void deleteUserReservationPT(MouseEvent event) {
        LocalDate date = datePickerPT.getValue();
        if(date != null) {
            LocalTime time = getTime(chooseHour.getSelectionModel().getSelectedItem());
            try {
                PersonalTrainerController.deleteReservation(LocalDateTime.of(date, time),currentUser);
                DeleteLabel.setText("Cancellato");
                DeleteLabel.setTextFill(Color.web("#0076a3"));
            } catch (SQLException e) {
                DeleteLabel.setText("Errore di connessione al database");
                DeleteLabel.setTextFill(Color.web("#ff0000"));
            }catch (Exception e){
                DeleteLabel.setText("Problemi con il servizio di mailing");
                DeleteLabel.setTextFill(Color.web("#ff0000"));
            }
        }
        else{
            DeleteLabel.setText("Seleziona una data prima di prenotare!");
            DeleteLabel.setTextFill(Color.web("#ff0000"));
        }
    }

    @FXML
    void deleteUserReservationUser(MouseEvent event) {
        LocalDate date = datePicker.getValue();
        if(date != null) {
            LocalTime time = getTime(chooseHour.getSelectionModel().getSelectedItem());
            try {
                userController.deleteReservation(LocalDateTime.of(date, time),currentUser);
                DeleteLabel.setText("Cancellato");
                DeleteLabel.setTextFill(Color.web("#0076a3"));
            } catch (SQLException e) {
                DeleteLabel.setText("Errore di connessione al database");
                DeleteLabel.setTextFill(Color.web("#ff0000"));
            }catch (Exception e){
                DeleteLabel.setText("Problemi con il servizio di mailing");
                DeleteLabel.setTextFill(Color.web("#ff0000"));
            }
        }
        else{
            DeleteLabel.setText("Seleziona una data prima di prenotare!");
            DeleteLabel.setTextFill(Color.web("#ff0000"));
        }
    }

    @FXML
    void BookAppointment(MouseEvent event) {
        LocalDate date = datePicker.getValue();
        if(date != null) {
            LocalTime time = getTime(chooseHour.getSelectionModel().getSelectedItem());
            try {
                String appointment = userController.bookAppointment(currentUser, LocalDateTime.of(date, time));
                if (appointment != null) {
                    BookingLabel.setText("Hai prenotato: " + appointment);
                    BookingLabel.setTextFill(Color.web("#0076a3"));
                } else {
                    BookingLabel.setText("Non ci sono posti disponibili nello slot selezionato oppure hai già prenotato");
                    BookingLabel.setTextFill(Color.web("#ff0000"));
                }
            } catch (SQLException e) {
                BookingLabel.setText("Errore di connessione al database");
                BookingLabel.setTextFill(Color.web("#ff0000"));
            }catch (Exception e){
                BookingLabel.setText("Problemi con il servizio di mailing");
                BookingLabel.setTextFill(Color.web("#ff0000"));
            }
        }
        else{
            BookingLabel.setText("Seleziona una data prima di prenotare!");
            BookingLabel.setTextFill(Color.web("#ff0000"));
        }
    }

    private LocalTime getTime(String hour){
        int time = 0;
        switch (hour) {
            case "8:00" -> time = 8;
            case "10:00" -> time = 10;
            case "14:00" -> time = 14;
            case "16:00" -> time = 16;
            case "18:00" -> time = 18;
        }
        return LocalTime.of(time,0);
    }

    @FXML
    void logInButtonPressed(MouseEvent event) throws IOException {
        currentUser = usernameField.getText();
        if(loginController.isAnExistingUser(currentUser) || loginController.isAnExsistingPersonalTrainer(currentUser)) {
            String password="";

            if(loginController.isAnExistingUser(currentUser))
             password = loginController.getUserPassword(currentUser);

            if(loginController.isAnExsistingPersonalTrainer(currentUser))
                password = loginController.getPersonalTrainerPassword(currentUser);

                if (Objects.equals(password, passwordField.getText())) {
                if (adminController.getUserType(currentUser) == 2) {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user-view.fxml")));

                } else if (adminController.getUserType(currentUser) == 3) {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("personaltrainer-view.fxml")));
                } else {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-view.fxml")));
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                inexistingUserLabel.setText("Password errata");
                inexistingUserLabel.setTextFill(Color.web("#ff0000"));
            }
        }else{
            inexistingUserLabel.setText("Nome utente inesistente");
            inexistingUserLabel.setTextFill(Color.web("#ff0000"));
        }
    }

    @FXML
    void signUpButtonPressed(MouseEvent event) {
        int type = 3;
        RadioButton button = (RadioButton) ruolo.getSelectedToggle();

        if(Objects.equals(button.getText(), "Utente"))
            type = 2;

        try {
            boolean registration = adminController.addUser(usernameField2.getText(), emailField2.getText(),type);
            if(registration){
                signUpSuccesful.setText("Registrazione completata!");
                signUpSuccesful.setTextFill(Color.web("#0076a3"));
            }else{
                signUpSuccesful.setText("Nome utente già in uso!");
                signUpSuccesful.setTextFill(Color.web("#ff0000"));
            }

        } catch (SQLException e){
            signUpSuccesful.setText("Registrazione fallita!");
            signUpSuccesful.setTextFill(Color.web("#ff0000"));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(chooseHour.getItems().isEmpty()){
            chooseHour.getItems().addAll(hours);
            chooseHour.setValue("8:00");
        }

        if(chooseHourPT.getItems().isEmpty()){
            chooseHourPT.getItems().addAll(hours);
            chooseHourPT.setValue("8:00");
        }

        try {
            UsersManager UsersManager = new UsersManager();
            AppointmentsManager AppointmentsManager = new AppointmentsManager();
            loginController = new LoginController(UsersManager);
            userController = new UserController(AppointmentsManager, UsersManager);
            PersonalTrainerController = new PersonalTrainerController(AppointmentsManager);
            adminController = new AdminController(UsersManager);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}