package com.example.cinema;

import DTO.MovieAndSeance;
import Model.TakenSeats;
import Model.Seances;
import Model.Movies;

import DTO.TSandMandS;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerMyTicketController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label errorLabel;
    @FXML
    ListView<TSandMandS> boughtTicketListView, resListView;
    @FXML
    Button deleteRes;
    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("MainCustomerView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setModel(Model model) {
        if (CustomerMyTicketController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        CustomerMyTicketController.model = model;
    }

    @FXML
    private void deleteReservation(ActionEvent actionEvent) throws IOException{
        try {
            model.getDatabase().deleteReservation(resListView.getSelectionModel().getSelectedItem().getTakenSeat().getId_seat(),model.getId());
            resListView.getItems().remove(resListView.getSelectionModel().getSelectedItem());

            errorLabel.setText("Usunięto rezerwację");
            errorLabel.setVisible(true);
        }catch (Exception e){
            errorLabel.setText("Nie udało się usunąć rezerwacji");
            errorLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TakenSeats> takenSeats = model.getDatabase().getTakenSeatsListForCustomer(model.getId());
        List<Movies> movies = model.getDatabase().getMoviesList();
        List<Seances> seances = model.getDatabase().getSeancesList();

        resListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TSandMandS>() {
            @Override
            public void changed(ObservableValue<? extends TSandMandS> observableValue, TSandMandS tSandMandS, TSandMandS t1) {
                deleteRes.setDisable(false);
            }
        });

        for(TakenSeats ts: takenSeats){
            if(ts.getId_customer() == model.getId()){
                x: for(Seances s: seances){
                    if(s.getId_seance() == ts.getId_seance()){
                        for(Movies m: movies){
                            if(m.getId_movie() == s.getId_movie()){
                                TSandMandS tSandMandS = new TSandMandS(ts,m,s);
                                if(ts.getReserved_or_taken() == 'R'){
                                    resListView.getItems().add(tSandMandS);
                                }else if(ts.getReserved_or_taken() == 'T'){
                                    boughtTicketListView.getItems().add(tSandMandS);
                                }
                                break x;
                            }
                        }
                    }
                }
            }
        }
    }
}
