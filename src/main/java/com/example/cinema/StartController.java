package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class StartController implements InitModel{
    Model model;
    DatabaseConnection databaseConnection;

    @FXML
    ListView <String> seancesListView;

    @Override
    public void initModel(Model model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        databaseConnection = model.getDatabase();


    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException{

    }

    @FXML
    private void goToRegistration(ActionEvent event) throws IOException{

    }
}
