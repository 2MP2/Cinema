package com.example.cinema;

import Model.ScreeningRooms;
import Model.TakenSeats;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerBuyController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;


    private int row,col;

    private char[][] tabART;
    private boolean[][] tabIsChoose;


    @FXML
    GridPane seatsGridPane;
    @FXML
    Rectangle screen;

    @FXML
    Button sellTicketButton;
    @FXML
    Spinner<Integer> normalTicketSpinner, reducedTicketSpinner;

    @FXML
    Label costLabel, idClientLabel;

    @FXML
    ChoiceBox<String> optionChoiceBox;

    @FXML
    private void sellTicket(ActionEvent actionEvent) throws IOException{
        int totalAmountOfTicket = 0;
        int amount = normalTicketSpinner.getValue() + reducedTicketSpinner.getValue();
        int minH = 450;

        for (int i = 0; i < row; i++){
            for(int j = 0;j <col; j++){
                if(tabIsChoose[i][j])
                    totalAmountOfTicket++;
            }
        }

        if(totalAmountOfTicket > amount)
        {
            idClientLabel.setText("za duzo biletów");
        }else if(totalAmountOfTicket < amount){
            idClientLabel.setText("za mało biletów");
        }else{
            try {
                if(optionChoiceBox.getSelectionModel().getSelectedItem().equals("Kup bilety"))
                {
                    model.getDatabase().insertTransactions(calculatePrice(),normalTicketSpinner.getValue(),reducedTicketSpinner.getValue(),
                            Model.getMovieAndSeance().getSeance().getId_seance(),model.getId());
                    for (int i = 0; i < row; i++){
                        for(int j = 0;j <col; j++){
                            if(tabIsChoose[i][j]){
                                model.getDatabase().insertTeakenSeats((char) ('A'+i),j+1,
                                        Model.getMovieAndSeance().getSeance().getId_seance(),
                                        model.getDatabase().getTransactionId(), model.getId());
                            }
                        }
                    }
                }else {
                    for (int i = 0; i < row; i++){
                        for(int j = 0;j <col; j++){
                            if(tabIsChoose[i][j]){
                                model.getDatabase().insertResTeakenSeats((char) ('A'+i),j+1,
                                        Model.getMovieAndSeance().getSeance().getId_seance(), model.getId());
                            }
                        }
                    }
                }

                fxmlLoader = new FXMLLoader(getClass().getResource("CustomerBuyView.fxml"));
                root = fxmlLoader.load();
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                ((Node)actionEvent.getSource()).getScene().getWindow().setWidth(55*col+250);
                ((Node) actionEvent.getSource()).getScene().getWindow().setHeight(Math.max(minH, 55 * row + 150));

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }catch (Exception e){
                idClientLabel.setText("ERROR");
                System.out.println(e.getMessage());
            }
        }
    }

    private double calculatePrice(){
        double total = 0;
        total = normalTicketSpinner.getValue() * Model.getMovieAndSeance().getSeance().getTicket_price() +
                reducedTicketSpinner.getValue()* Model.getMovieAndSeance().getSeance().getTicket_price() * 0.8;
        return Math.round(total *100.00)/100.00;
    }

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
        tabIsChoose = new boolean[row][col];

        for (int i = 0; i < row; i++){
            for(int j = 0;j <col; j++){
                tabART[i][j] = 'A';
                tabIsChoose[i][j] = false;
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
                }else if(tabART[i-1][j]=='T'){
                    button.setStyle("-fx-background-color: #ff0000");
                    button.setDisable(true);
                }

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String[] id = ((Button)actionEvent.getSource()).getText().split(" ");
                        int idRow = (id[0].charAt(0) - 'A');
                        int idCol = Integer.parseInt(id[1]) - 1;

                        if(!tabIsChoose[idRow][idCol]){
                            button.setStyle("-fx-background-color: #111bb4");
                            tabIsChoose[idRow][idCol]=true;

                        }else {
                            button.setStyle("-fx-background-color: #00ff00");
                            tabIsChoose[idRow][idCol] = false;
                        }
                    }
                });
                button.setPrefSize(50,50);

                seatsGridPane.add(button,j, i);
                seatsGridPane.setVgap(5);
                seatsGridPane.setHgap(5);
            }
        }

    }

    @FXML
    private void goToMenu(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("MainCustomerView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        ((Node)actionEvent.getSource()).getScene().getWindow().setHeight(420);
        ((Node)actionEvent.getSource()).getScene().getWindow().setWidth(620);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setModel(Model model) {
        if (CustomerBuyController.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        CustomerBuyController.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildSeats();

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,row*col/2);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,row*col/2);
        valueFactory1.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                costLabel.setText("Koszt przy zakupie: " + calculatePrice() + " zł");
            }
        });

        valueFactory2.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                costLabel.setText("Koszt przy zakupie: " + calculatePrice() + " zł");
            }
        });

        valueFactory1.setValue(0);
        valueFactory2.setValue(0);

        normalTicketSpinner.setValueFactory(valueFactory1);
        reducedTicketSpinner.setValueFactory(valueFactory2);

        optionChoiceBox.getItems().add("Kup bilety");
        optionChoiceBox.getItems().add("Zarezerwuj bilety");
        optionChoiceBox.getSelectionModel().selectFirst();
    }
}
