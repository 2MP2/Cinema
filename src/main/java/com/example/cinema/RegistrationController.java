package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController implements InitModel{
    Model model;
    DatabaseConnection databaseConnection;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField loginTextField,passwordTextField,nameTextField,surnameTextField,phoneTextField;

    @Override
    public void initModel(Model model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        databaseConnection = model.getDatabase();

    }

    @FXML
    public void registration(ActionEvent actionEvent) throws IOException {

    }

    @FXML
    public void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("StartView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        System.out.println("XXXXXXXXXXXXX");
        StartController startController = fxmlLoader.getController();
        startController.initModel(model);
        System.out.println("XXXXXXXXXXXXX");
        //startController.fillList();
        stage.setScene(scene);
        stage.show();
    }
}
