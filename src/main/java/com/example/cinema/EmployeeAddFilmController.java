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
    private TextField titleTextArea,lengthTextArea,distributorTextArea;
    @FXML
    private DatePicker getDataPicker,returnDataPicker;
    @FXML
    private ChoiceBox<String> dslChoiceBox,is3DChoiceBox;
    @FXML
    private Label errorLabel;
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
    private void addFilm(ActionEvent actionEvent) throws IOException{
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
            timestamp1 = new Timestamp(getDataPicker.getValue().getYear()-1900,getDataPicker.getValue().getMonthValue()-1,getDataPicker.getValue().getDayOfMonth(),0,0,0,0 );
            timestamp2 = new Timestamp(returnDataPicker.getValue().getYear()-1900,returnDataPicker.getValue().getMonthValue()-1,returnDataPicker.getValue().getDayOfMonth(),0,0,0,0 );

        }catch (Exception e)
        {
            errorLabel.setText("Problem z datami");
            errorLabel.setVisible(true);
            return;
        }

        char dsl = 'X';
        try {
            if(dslChoiceBox.getSelectionModel().getSelectedItem().equals("Dubbing")){
                dsl = 'D';
            }else if (dslChoiceBox.getSelectionModel().getSelectedItem().equals("Napisy")){
                dsl = 'S';
            }else if (dslChoiceBox.getSelectionModel().getSelectedItem().equals("Lektor")){
                dsl = 'L';
            }
        }catch (Exception e){
            errorLabel.setText("Nie wybrano dub/nap/lek");
            errorLabel.setVisible(true);
            return;
        }

        boolean is3D = false;
        try {
            if(is3DChoiceBox.getSelectionModel().getSelectedItem().equals("2D")){
                is3D = false;
            }else if (is3DChoiceBox.getSelectionModel().getSelectedItem().equals("3D")){
                is3D = true;
            }
        }catch (Exception e){
            errorLabel.setText("Nie wybrano 2D/3D");
            errorLabel.setVisible(true);
            return;
        }

        try {
            model.getDatabase().insertMovie(titleTextArea.getText(),min,distributorTextArea.getText(),timestamp1,timestamp2,dsl,is3D);
            errorLabel.setText("DODANO FILM");
            errorLabel.setVisible(true);
        }catch (Exception  e)
        {
            if(e.getMessage().equals("ORA-01438"))
            {
                errorLabel.setText("Za długi film");
                errorLabel.setVisible(true);
            }else {
                errorLabel.setText("ERROR");
                errorLabel.setVisible(true);
                System.out.println(e.getMessage());
            }

        }


    }
}
