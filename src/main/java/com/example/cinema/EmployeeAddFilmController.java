package com.example.cinema;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class EmployeeAddFilmController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void setModel(Model model) {
        if(EmployeeAddFilmController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeAddFilmController.model = model;
    }

    @FXML
    TextField titleTextArea,lengthTextArea,distributorTextArea;
    @FXML
    DatePicker getDataPicker,returnDataPicker;
    @FXML
    ChoiceBox<String> dslChoiceBox,is3DChoiceBox;
    @FXML
    Label errorLabel;
    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {


        fxmlLoader = new FXMLLoader(getClass().getResource("MainEmployeeView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dslChoiceBox.getItems().add("Dubbing");
        dslChoiceBox.getItems().add("Napisy");
        dslChoiceBox.getItems().add("Lektor");

        is3DChoiceBox.getItems().add("2D");
        is3DChoiceBox.getItems().add("3D");
    }

    @FXML
    public void addFilm(ActionEvent actionEvent) throws IOException{
        int min;
        try {
            min = Integer.parseInt(lengthTextArea.getText());
        }catch (Exception e )
        {
            errorLabel.setText("Zła długość filmu");
            errorLabel.setVisible(true);
            return;
        }

        Timestamp timestamp1;
        Timestamp timestamp2;

        try {

        }catch (Exception e)
        {
            errorLabel.setText("Problem z datami");
            errorLabel.setVisible(true);
            return;
        }

        model.getDatabase().insertMovie(titleTextArea.getText(),min,distributorTextArea,);

    }
}
