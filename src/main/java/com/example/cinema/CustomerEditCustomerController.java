package com.example.cinema;

import Model.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerEditCustomerController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PasswordField passwordField;
    @FXML
    TextField nameTextField, surnameTextField, teleTextField;
    @FXML
    Label currentName, currentSurname, currentTele, errorLabel, errorLabel2;

    @FXML
    CheckBox confirmCheckBox;
    @FXML
    private void changeCustomer(ActionEvent actionEvent) throws IOException {
        boolean isChanged = false;
        try {

            if(!(nameTextField.getText() == null || nameTextField.getText().trim().isEmpty())){
                currentName.setText(nameTextField.getText());
                isChanged = true;
            }

            if(!(surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty())){
                currentSurname.setText(surnameTextField.getText());
                isChanged = true;
            }

            if(!(teleTextField.getText() == null || teleTextField.getText().trim().isEmpty())){

                try {
                    Integer.parseInt(teleTextField.getText());
                    if(teleTextField.getText().length()!=9){
                        throw new IllegalStateException("za krutki numer") ;
                    }
                }catch (NumberFormatException  e){
                    errorLabel.setVisible(true);
                    errorLabel.setText("Numer zawiera niedozwoloneznaki");
                    return;
                }catch (Exception e){
                    errorLabel.setVisible(true);
                    errorLabel.setText("Numer jest za krótki");
                    return;
                }

                currentTele.setText(teleTextField.getText());
                isChanged = true;
            }

            if(passwordField.getText() == null || passwordField.getText().trim().isEmpty()){
                if(isChanged) {
                    model.getDatabase().changeCustomer(model.getId(), "", currentName.getText(), currentSurname.getText(), currentTele.getText());
                    isChanged = true;
                }
            }else{
                model.getDatabase().changeCustomer(model.getId(),passwordField.getText(),currentName.getText(),currentSurname.getText(),currentTele.getText());
                isChanged = true;
            }

            if(isChanged) {
                errorLabel.setText("Zmieniono");
                errorLabel.setVisible(true);
            }

        } catch (Exception e) {
            if (e.getMessage().contains("ORA-00001")) {
                errorLabel.setText("Numer jest juz w bazie");
                errorLabel.setVisible(true);
            }else
            {
                errorLabel.setText("Błąd w danych");
                errorLabel.setVisible(true);
            }
        }
    }

    @FXML
    private void deleteAccount(ActionEvent actionEvent) throws IOException{
        if(confirmCheckBox.isSelected()){
            try {
                model.getDatabase().customerAccountDeactivation(model.getId());
                model.clear();

                fxmlLoader = new FXMLLoader(getClass().getResource("StartView.fxml"));
                root = fxmlLoader.load();
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                errorLabel2.setText("Nie udało się usunąć kąta");
                errorLabel2.setVisible(true);
            }
        }else{
            errorLabel2.setText("Potwierdź");
            errorLabel2.setVisible(true);
        }
    }
    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("MainCustomerView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setModel(Model model) {
        if (CustomerEditCustomerController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        CustomerEditCustomerController.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Customers> customersList = model.getDatabase().getCustomersList();

        for (Customers c: customersList)
        {
            if(model.getId() == c.getId_customer()){
                currentName.setText(c.getName());
                currentSurname.setText(c.getSurname());
                currentTele.setText(c.getPhone_number());
            }
        }


    }
}
