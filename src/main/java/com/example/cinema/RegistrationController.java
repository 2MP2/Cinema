package com.example.cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController{

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField loginTextField,passwordTextField,nameTextField,surnameTextField,phoneTextField;

    @FXML
    private Label errorLabel;


    @FXML
    private void registration(ActionEvent actionEvent) throws IOException {
        boolean isReg = false;

        try {
            model.getDatabase().insertCustomer(loginTextField.getText(),passwordTextField.getText(),nameTextField.getText(),surnameTextField.getText(),passwordTextField.getText());
            isReg =true;
        }
        catch (NullPointerException e){
            errorLabel.setVisible(true);
            errorLabel.setText("Brak danej");
        }
        catch (Exception e){
            if(e.getMessage().contains("ORA-00001")){
                errorLabel.setVisible(true);
                errorLabel.setText("Login juz istnieje");
                System.out.println(e.getMessage());
            }
            else
            {
                errorLabel.setVisible(true);
                errorLabel.setText("ERROR");
            }


        }

        if(isReg){
            root = FXMLLoader.load(getClass().getResource("MainCustomerView.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
        if(RegistrationController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        RegistrationController.model = model;
    }
}
