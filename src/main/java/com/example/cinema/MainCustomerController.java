package com.example.cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainCustomerController {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void setModel(Model model) {
        if(MainCustomerController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        MainCustomerController.model = model;
    }

    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        model.setCurrentCustomer(null);

        fxmlLoader = new FXMLLoader(getClass().getResource("StartView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
