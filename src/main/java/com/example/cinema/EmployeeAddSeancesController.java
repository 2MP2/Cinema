package com.example.cinema;

import Model.Movies;
import Model.ScreeningRooms;
import Model.Seances;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeAddSeancesController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private DatePicker startDatePicker,endDatePicker;
    @FXML
    private TextField priceTextField;
    @FXML
    private ChoiceBox<Movies> filmChoiceBox;
    @FXML
    private ChoiceBox<ScreeningRooms> screeningRoomChoiceBox;
    @FXML
    private Label errorLabel;
    @FXML
    private Spinner<Integer> startHourSpinner,startMinuteSpinner,endHourSpinner,endMinuteSpinner;

    public static void setModel(Model model) {
        if(EmployeeAddSeancesController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeAddSeancesController.model = model;
    }

    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource("MainEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addSeance(ActionEvent actionEvent) throws IOException {
        Timestamp timestamp1;
        Timestamp timestamp2;

        try {
            timestamp1 = new Timestamp(startDatePicker.getValue().getYear()-1900,startDatePicker.getValue().getMonthValue()-1,startDatePicker.getValue().getDayOfMonth(),
                                            startHourSpinner.getValue(),startMinuteSpinner.getValue(),0,0 );
            timestamp2 = new Timestamp(endDatePicker.getValue().getYear()-1900,endDatePicker.getValue().getMonthValue()-1,endDatePicker.getValue().getDayOfMonth(),
                                            endHourSpinner.getValue(),endMinuteSpinner.getValue(),0,0 );

        }catch (Exception e)
        {
            errorLabel.setText("Problem z datami");
            errorLabel.setVisible(true);
            return;
        }

        double price = 0;
        try {
            price = Math.round(Double.parseDouble(priceTextField.getText()) * 100.00) / 100.00; ;
        }catch (Exception e){
            errorLabel.setText("Problem ceną");
            errorLabel.setVisible(true);
            return;
        }

        Movies movie;
        ScreeningRooms screeningRoom;

        try {
            movie = filmChoiceBox.getValue();
            screeningRoom = screeningRoomChoiceBox.getValue();

        }catch (Exception e){
            errorLabel.setText("Nie wybrano filmu bądź sali");
            errorLabel.setVisible(true);
            return;
        }

        Calendar cal =Calendar.getInstance();
        cal.setTime(timestamp1);
        cal.add(Calendar.MINUTE, movie.getLength());
        timestamp1 = new Timestamp(cal.getTime().getTime());

        int seanceDate = timestamp2.compareTo(timestamp1);

        if(seanceDate <=0){
                errorLabel.setText("Zły data lub czas");
                errorLabel.setVisible(true);
                return;
        }

        List<Seances> seances = model.getDatabase().getSeancesList();
        for (Seances s: seances){
            if(s.getId_screening_room() == screeningRoomChoiceBox.getSelectionModel().getSelectedItem().getId_screening_room()){
                if(!(timestamp1.after(s.getEnd_time()) || timestamp2.before(s.getStart_time()))){
                    errorLabel.setText("JUŻ ISTNIEJE SEANSE W TYM TERMINIE");
                    errorLabel.setVisible(true);
                    return;
                }

            }
        }

        try {
            model.getDatabase().insertSeance(timestamp1,timestamp2,price,movie.getId_movie(),screeningRoom.getId_screening_room());

            errorLabel.setText("DODANO SEANS");
            errorLabel.setVisible(true);
        }catch (Exception e){
            errorLabel.setText("ERROR");
            errorLabel.setVisible(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(timestamp.getHours(),23);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(timestamp.getSeconds(),59);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(timestamp.getHours(),23);
        SpinnerValueFactory<Integer> valueFactory4= new SpinnerValueFactory.IntegerSpinnerValueFactory(timestamp.getSeconds(),59);

        valueFactory1.setValue(23);
        valueFactory2.setValue(59);
        valueFactory3.setValue(23);
        valueFactory4.setValue(59);

        startHourSpinner.setValueFactory(valueFactory1);
        startMinuteSpinner.setValueFactory(valueFactory2);

        endHourSpinner.setValueFactory(valueFactory3);
        endMinuteSpinner.setValueFactory(valueFactory4);

        List<Movies> movies = model.getDatabase().getMoviesList();
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for(Movies m: movies){
            filmChoiceBox.getItems().add(m);
        }

        for(ScreeningRooms s:screeningRooms){
            screeningRoomChoiceBox.getItems().add(s);
        }

        startDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });

        endDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });
    }

}
