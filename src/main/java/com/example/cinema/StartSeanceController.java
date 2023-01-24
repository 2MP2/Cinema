package com.example.cinema;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Model.ScreeningRooms;
import Model.TakenSeats;
public class StartSeanceController implements Initializable {
    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int row,col;

    private char[][] tabART;

    @FXML
    GridPane seatsGridPane;

    @FXML
    Rectangle screen;

    @FXML
    Label titleLabel;

    private void buildSeats(){
        List<ScreeningRooms> screeningRooms = model.getDatabase().getScreeningRoomsList();

        for (ScreeningRooms sr: screeningRooms){
            if(sr.getId_screening_room() == Model.getMovieAndSeance().getSeance().getId_screening_room()){
                row = sr.getAmount_of_rows();
                col = sr.getAmount_of_columns();
                break;
            }
        }

        List<TakenSeats> takenSeats = model.getDatabase().getTakenSeatsListForSeance(Model.getMovieAndSeance().getSeance().getId_seance());
        tabART = new char[row][col];

        for (int i = 0; i < row; i++){
            for(int j = 0;j <col; j++){
                tabART[i][j] = 'A';
            }
        }

        for (TakenSeats ts: takenSeats){
            if(ts.getReserved_or_taken() == 'T')
                tabART[(ts.getRow_identifier() - 'A')][ts.getColumn_identifier()-1] = 'T';
            else if(ts.getReserved_or_taken() == 'R'){
                tabART[(ts.getRow_identifier() - 'A')][ts.getColumn_identifier()-1] = 'T';
            }
        }
        screen.setWidth(col*55);

        for(int i = 1; i <row+1; i++){
            seatsGridPane.getRowConstraints().add(new RowConstraints(50));
            for (int j = 0;j <col; j ++){
                if(i == 1)
                    seatsGridPane.getColumnConstraints().add(new ColumnConstraints(50));
                Button button = new Button(Character.toString(i - 1+ 'A')+ " " + (j + 1));

                if(tabART[i-1][j]=='A'){
                    button.setStyle("-fx-background-color: #00ff00");
                    button.setDisable(true);
                }else if(tabART[i-1][j]=='T'){
                    button.setStyle("-fx-background-color: #ff0000");
                    button.setDisable(true);
                }

                button.setPrefSize(50,50);

                seatsGridPane.add(button,j, i);
                seatsGridPane.setVgap(5);
                seatsGridPane.setHgap(5);
            }
        }

    }

    public static void setModel(Model model) {
        if (StartSeanceController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        StartSeanceController.model = model;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(Model.getMovieAndSeance().getMovie().getTitle());
        buildSeats();
    }
}
