package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartController implements InitModel{
    Model model;
    DatabaseConnection databaseConnection;



    @Override
    public void initModel(Model model) {
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
