package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements InitModel{
    Model model;
    DatabaseConnection databaseConnection;
    @FXML
    TextField loginTextField,passwordTextField;

    @Override
    public void initModel(Model model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        databaseConnection = model.getDatabase();

    }

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {

    }
    @FXML
    public void goToMenu(ActionEvent actionEvent) throws IOException {

    }
}
