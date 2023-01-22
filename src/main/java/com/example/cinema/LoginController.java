package com.example.cinema;

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
    private TextField loginTextField,passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox <String> choiceBox;

    @FXML
    private void login(ActionEvent actionEvent) throws IOException {


        if(choiceBox.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("Wybierz opcje logowania");
        }
        else if(choiceBox.getSelectionModel().getSelectedItem().equals("Klient")) {
            long idCustomer;

            try {
                idCustomer = model.getDatabase().logInCustomer(loginTextField.getText(),passwordTextField.getText());
                model.setId(idCustomer);
                model.setLogin(loginTextField.getText());
            }catch (Exception e){
                idCustomer = 0;
                errorLabel.setVisible(true);
                errorLabel.setText("Błąd ");
                System.out.println(e.getMessage());
            }

            if(idCustomer>0){ //jeśli id jest zero to dane logowani nie istnieją (zapytanie zwraca 0)
                model.setId(idCustomer);
                model.setLogin(loginTextField.getText());

                root = FXMLLoader.load(getClass().getResource("MainCustomerView.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        else if(choiceBox.getSelectionModel().getSelectedItem().equals("Pracownik")){
            long idEmployye;

            try {
                idEmployye = model.getDatabase().logInEmployee(loginTextField.getText(),passwordTextField.getText());
                model.setId(idEmployye);
                model.setLogin(loginTextField.getText());
            }catch (Exception e){
                idEmployye = 0;
                errorLabel.setVisible(true);
                errorLabel.setText("Błąd");
            }

            if(idEmployye>0){ //jeśli id jest zero to dane logowani nie istnieją (zapytanie zwraca 0)
                root = FXMLLoader.load(getClass().getResource("MainEmployeeView.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                model.setId(idEmployye);
                model.setLogin(loginTextField.getText());

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
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
