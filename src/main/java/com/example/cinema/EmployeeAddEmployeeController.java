package com.example.cinema;

import Model.Positions;

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
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeAddEmployeeController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label errorLabel;

    @FXML
    TextField loginTextField, passwordTextField, nameTextField, surnameTextField, phoneTextField;

    @FXML
    ChoiceBox<Positions> positionChoiceBox;

    public static void setModel(Model model) {
        if (EmployeeAddEmployeeController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeAddEmployeeController.model = model;
    }

    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {


        fxmlLoader = new FXMLLoader(getClass().getResource("MainEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addEmployee(ActionEvent actionEvent) throws IOException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            model.getDatabase().insertEmployee(loginTextField.getText(), passwordTextField.getText(), nameTextField.getText(), surnameTextField.getText(), phoneTextField.getText(), timestamp, positionChoiceBox.getSelectionModel().getSelectedItem().getId_position());

            errorLabel.setText("DODANO PRACOWNIKA ");
            errorLabel.setVisible(true);
        } catch (NullPointerException e) {
            errorLabel.setVisible(true);
            errorLabel.setText("Brak danej");
        } catch (Exception e) {
            if (e.getMessage().contains("ORA-00001")) {
                errorLabel.setVisible(true);
                errorLabel.setText("Login juz istnieje");
                System.out.println(e.getMessage());
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("ERROR");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Positions> list = model.getDatabase().getPositionsList();

        for(Positions p: list){
            positionChoiceBox.getItems().add(p);
        }
    }
}