package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Seances;
import Model.Movies;
import javafx.stage.Stage;


public class StartController implements InitModel{
    Model model;
    DatabaseConnection databaseConnection;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ListView <String> seancesListView;

    @Override
    public void initModel(Model model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        databaseConnection = model.getDatabase();

        fillList();
    }

     void fillList(){
        List<Seances> listS = databaseConnection.getSeancesList();
        List<Movies> listM = databaseConnection.getMoviesList();

        for (Seances s: listS)
        {
            String title = "";
            int length = 0;
            char dub_sub_lec = 'A';
            boolean is3D = false;

            for(Movies m:listM)
            {
                if(s.getId_movie()==m.getId_movie())
                {
                    title = m.getTitle();
                    length = m.getLength();
                    dub_sub_lec = m.getDub_sub_lec();
                    is3D = m.isIs3D();
                }
            }
            String fullString = s.getId_seance() + " " +
                    s.getStart_time() + " " +
                    s.getEnd_time() + " " +
                    s.getTicket_price() + " " +
                    title + " " + length + " " + dub_sub_lec + " " + is3D + " " +
                    s.getId_screening_room();

            seancesListView.getItems().add(fullString);
        }
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
}
