package com.example.cinema;

import DTO.MovieAndSeance;
import Model.ScreeningRooms;
import Model.Seances;
import Model.TakenSeats;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeSeancesController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int row,col;

    private char[][] seats;


    @FXML
    GridPane seatsGridPane;
    @FXML
    Rectangle screen;


    public static void setModel(Model model) {
        if(EmployeeSeancesController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        EmployeeSeancesController.model = model;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for (ScreeningRooms sr: screeningRooms){
            if(sr.getId_screening_room() == Model.movieAndSeance.getS().getId_screening_room()){
                row = sr.getAmount_of_rows();
                col = sr.getAmount_of_columns();
            }
        }

        List<TakenSeats> takenSeats = model.getDatabase().getTakenSeatsList();
        seats = new char[row][col];




        screen.setWidth(row*55);
        for(int i = 0; i <row; i++){
            seatsGridPane.getColumnConstraints().add(new ColumnConstraints(50));
            for (int j = 1;j <col+1; j ++){
                if(i == 0)
                    seatsGridPane.getRowConstraints().add(new RowConstraints(50));
                seats[i][j-1] = 'A';
                Button button = new Button(Character.toString(j - 1+ 'A') + " " + (i + 1));
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String[] id = ((Button)actionEvent.getSource()).getText().split(" ");
                        int idRow = (int)(id[0].charAt(0) - 'A');
                        int idCol = Integer.parseInt(id[1]) - 1;

                        System.out.println(idRow + " " + idCol);

                    }
                });
                button.setPrefSize(50,50);

                seatsGridPane.add(button,i, j);
                seatsGridPane.setVgap(5);
                seatsGridPane.setHgap(5);
            }
        }
    }
}
