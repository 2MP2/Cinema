package com.example.cinema;

import Model.Movies;
import Model.Seances;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button goToBuyTicket,goBookTicket,goToMyReservations,goToEditCustomer;

    @FXML
    ListView<Seances> seancesListView;
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

        fxmlLoader = new FXMLLoader(getClass().getResource(""));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goBookTicket(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource(""));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToMyReservations(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource(""));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToEditCustomer(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource(""));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Seances> listS = model.getDatabase().getSeancesList();
        List<Movies> listM = model.getDatabase().getMoviesList();

        for (Seances s: listS)
        {
            String title = "";
            int length = 0;
            char dub_sub_lec = 'X';
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

            String full = title + " "+ length + "min "
                    + Math.round(s.getTicket_price() * 100.00)/100.00 + "zł "
                    + s.getStart_time().getHours() + ":" + ((s.getStart_time().getMinutes()<10) ? "0" : "") + s.getStart_time().getMinutes() + "-"
                    + s.getEnd_time().getHours() + ":" + s.getEnd_time().getMinutes()
                    + ((Character.toUpperCase(dub_sub_lec) == 'D') ? " DUBBING"
                    : ((Character.toUpperCase(dub_sub_lec) == 'S') ? " NAPISY"
                    : ((Character.toUpperCase(dub_sub_lec) == 'L') ? " LECTOR" : " XXX")))
                    + ((is3D) ? " 3D" : " 2D");


            seancesListView.getItems().add(full);
        }

    }



}
