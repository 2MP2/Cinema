package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField loginTextField,passwordTextField;
    @FXML
    Label errorLabel;
    @FXML
    ChoiceBox <String> choiceBox;

    @FXML
    private void login(ActionEvent actionEvent) throws IOException {


        if(choiceBox.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("Wybierz opcje logowania");
        }
        else if(choiceBox.getSelectionModel().getSelectedItem().equals("Klient")) {
            if(true){ //warunek zawierajace metode z databes logowania
                root = FXMLLoader.load(getClass().getResource("MainCustomerView.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                errorLabel.setVisible(true);
                errorLabel.setText("Błędny login lub hasło");
            }
        }
        else if(choiceBox.getSelectionModel().getSelectedItem().equals("Pracownik")){

            if(true){ //warunek zawierajace metode z databes logowania
                root = FXMLLoader.load(getClass().getResource("MainEmployeeView.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                errorLabel.setVisible(true);
                errorLabel.setText("Błędny login lub hasło");
            }

        }
    }
    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("StartView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setModel(Model model) {
        if(LoginController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        LoginController.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().add("Klient");
        choiceBox.getItems().add("Pracownik");
    }
}
