package com.example.cinema;

import DTO.MovieAndSeance;
import Model.Movies;
import Model.Seances;
import Model.ScreeningRooms;

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
import java.util.List;
import java.util.ResourceBundle;

public class MainCustomerController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button goToBuyTicket,goBookTicket,goToEditCustomer;
    @FXML
    private Label idLabel;

    @FXML
    ListView<MovieAndSeance> seancesListView;

    public static void setModel(Model model) {
        if(MainCustomerController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        MainCustomerController.model = model;
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
    private void goToBuyTicket(ActionEvent actionEvent) throws IOException {

        int row = 10;
        int col = 10;
        int minH = 450;


        Model.setMovieAndSeance( seancesListView.getSelectionModel().getSelectedItem());
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for (ScreeningRooms sr: screeningRooms){
            if(sr.getId_screening_room() == Model.getMovieAndSeance().getSeance().getId_screening_room()){
                row = sr.getAmount_of_rows();
                col = sr.getAmount_of_columns();
                break;
            }
        }

        fxmlLoader = new FXMLLoader(getClass().getResource("CustomerBuyView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        ((Node)actionEvent.getSource()).getScene().getWindow().setWidth(55*col+250);
        ((Node) actionEvent.getSource()).getScene().getWindow().setHeight(Math.max(minH, 55 * row + 150));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToMyReservations(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource("CustomerMyTicketView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToEditCustomer(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource("CustomerEditCustomerView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setText("KLIENT: " + model.getLogin());

        seancesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MovieAndSeance>() {
            @Override
            public void changed(ObservableValue<? extends MovieAndSeance> observableValue, MovieAndSeance movieAndSeance, MovieAndSeance t1) {
                goToBuyTicket.setDisable(false);
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
    }




}
