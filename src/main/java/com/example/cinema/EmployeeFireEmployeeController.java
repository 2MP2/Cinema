package com.example.cinema;

import Model.Employees;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFireEmployeeController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ChoiceBox <Employees> employeeChoiceBox;
    @FXML
    CheckBox confirmCheckBox;
    @FXML
    Label errorLabel;

    public static void setModel(Model model) {
        if(EmployeeFireEmployeeController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeFireEmployeeController.model = model;
    }

    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeEditEmployView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void fireEmployee(ActionEvent actionEvent) throws IOException{
        if(confirmCheckBox.isSelected()) {
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                model.getDatabase().fireEmployee(employeeChoiceBox.getSelectionModel().getSelectedItem().getId_employee(),timestamp);
                errorLabel.setText("ZWOLNIONO");
                errorLabel.setVisible(true);
            }catch (Exception e){
                errorLabel.setText("ERROR");
                errorLabel.setVisible(true);
            }

        }else {
            errorLabel.setText("POTWIERDŹ");
            errorLabel.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Employees> list = model.getDatabase().getEmployeesList();

        for (Employees e:list){
            employeeChoiceBox.getItems().add(e);
        }
    }
}
