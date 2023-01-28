package com.example.cinema;

import Model.Seances;
import Model.Movies;
import Model.ScreeningRooms;

import DTO.MovieAndSeance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainEmployeeController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label idLabel;
    @FXML
    private Button editEmployeeButton, goToSeancesButton;
    @FXML
    ListView<MovieAndSeance> seancesListView;


    public static void setModel(Model model) {
        if(MainEmployeeController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        MainEmployeeController.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        seancesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MovieAndSeance>() {
            @Override
            public void changed(ObservableValue<? extends MovieAndSeance> observableValue, MovieAndSeance movieAndSeance, MovieAndSeance t1) {
                goToSeancesButton.setDisable(false);
            }
        });

        List<Seances> listS = model.getDatabase().getSeancesList();
        List<Movies> listM = model.getDatabase().getMoviesList();

        for (Seances s: listS)
        {
            for(Movies m:listM)
            {
                if(s.getId_movie()==m.getId_movie())
                {
                    MovieAndSeance movieAndSeance = new MovieAndSeance(m,s);
                    seancesListView.getItems().add(movieAndSeance);
                    break;
                }
            }
        }

        try {
            if(model.getDatabase().isEmployeeAnManager(model.getId()))
            {
                idLabel.setText("Kierownik ID: " + model.getId());
                editEmployeeButton.setVisible(true);
            }
            else{
                editEmployeeButton.setVisible(false);
                idLabel.setText("Pracownik ID: " + model.getId());
            }

        } catch (SQLException e) {
            System.out.println("Błąd");
            editEmployeeButton.setVisible(false);
            idLabel.setText("Pracownik ID: " + model.getId());
        }
    }

    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        model.clear();

        fxmlLoader = new FXMLLoader(getClass().getResource("StartView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToSeances(ActionEvent actionEvent) throws IOException {

        int row = 10;
        int col = 10;
        int minSceneHeight = 400;


        Model.setMovieAndSeance( seancesListView.getSelectionModel().getSelectedItem());
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for (ScreeningRooms sr: screeningRooms){
            if(sr.getId_screening_room() == Model.getMovieAndSeance().getSeance().getId_screening_room()){
                row = sr.getAmount_of_rows();
                col = sr.getAmount_of_columns();
                break;
            }
        }

        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeSeancesView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,55*col+250,Math.max(minSceneHeight, 55 * row + 150));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToAddFilm(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeAddFilmView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToAddSeances(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeAddSeancesView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToEditEmployee(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeEditEmployView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
