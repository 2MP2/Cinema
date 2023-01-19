package com.example.cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeEditEmployeeController {
    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void setModel(Model model) {
        if(EmployeeEditEmployeeController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeEditEmployeeController.model = model;
    }

    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {


        fxmlLoader = new FXMLLoader(getClass().getResource("MainEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToAddEmployee(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeAddEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToFireEmployee(ActionEvent actionEvent) throws IOException {

        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeFireEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
