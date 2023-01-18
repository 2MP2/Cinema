package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Seances;
import Model.Movies;
import javafx.stage.Stage;


public class StartController implements Initializable{
    private static  Model model = null;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ListView <String> seancesListView;

     void fillList(){
        List<Seances> listS = model.getDatabase().getSeancesList();
        List<Movies> listM = model.getDatabase().getMoviesList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillList();
    }

    public static void setModel(Model model) {
        if(StartController.model != null)
            System.out.println("CCCC");

        StartController.model = model;
    }
}
