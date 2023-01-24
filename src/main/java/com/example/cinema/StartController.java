package com.example.cinema;

import DTO.MovieAndSeance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Model.Seances;
import Model.Movies;
import Model.ScreeningRooms;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class StartController implements Initializable{
    private static  Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ListView <MovieAndSeance> seancesListView;

    private void goToSeances(MouseEvent actionEvent) throws IOException {
        int row = 10;
        int col = 10;
        int minSceneHeight = 320;
        int minSceneWidth = 450;

        Model.setMovieAndSeance( seancesListView.getSelectionModel().getSelectedItem());
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for (ScreeningRooms sr: screeningRooms){
            if(sr.getId_screening_room() == Model.getMovieAndSeance().getSeance().getId_screening_room()){
                row = sr.getAmount_of_rows();
                col = sr.getAmount_of_columns();
                break;
            }
        }

        fxmlLoader = new FXMLLoader(getClass().getResource("StartSeanceView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,Math.max(minSceneWidth, 55*col+50),Math.max(minSceneHeight, 55 * row + 150));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToRegistration(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("RegistrationView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        seancesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    goToSeances(mouseEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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


    public static void setModel(Model model) {
        if(StartController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        StartController.model = model;
    }
}
