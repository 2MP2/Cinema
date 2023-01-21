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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EmployeeSeancesController implements Initializable {

    private static Model model;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int row,col;

    private char[][] tabART;
    private boolean[][] tabIsChoose;

    Map<String,Long > idCustomerMap, idSeatMap;

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

        ((Node)actionEvent.getSource()).getScene().getWindow().setHeight(410);
        ((Node)actionEvent.getSource()).getScene().getWindow().setWidth(630);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sellTicket(ActionEvent actionEvent) throws IOException{
        int totalAmountOfTicket = 0;
        int amount = normalTicketSpinner.getValue() + reducedTicketSpinner.getValue();
        int minH = 400;

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
                model.getDatabase().insertTransactions(calculatePrice(),normalTicketSpinner.getValue(),reducedTicketSpinner.getValue(),
                        Model.getMovieAndSeance().getSeance().getId_seance(),0);
                for (int i = 0; i < row; i++){
                    for(int j = 0;j <col; j++){
                        if(tabIsChoose[i][j])
                            if(tabART[i][j] == 'R'){
                                model.getDatabase().deleteReservation(
                                        idSeatMap.get(Character.toString(i + 'A')+ " " + (j + 1)),
                                        idCustomerMap.get(Character.toString(i + 'A')+ " " + (j + 1))
                                );

                                model.getDatabase().insertTeakenSeats((char) ('A'+i),j+1,
                                        Model.getMovieAndSeance().getSeance().getId_seance(),
                                        model.getDatabase().getTransactionId(),
                                        0);
                            }else{
                                model.getDatabase().insertTeakenSeats((char) ('A'+i),j+1,
                                        Model.getMovieAndSeance().getSeance().getId_seance(),
                                        model.getDatabase().getTransactionId(),
                                        0);
                            }
                    }
                }

                fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeSeancesView.fxml"));
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
                tabART[(ts.getRow_identifier() - 'A')][ts.getColumn_identifier()-1] = 'R';
                idCustomerMap.put(ts.getRow_identifier() +" "+ ts.getColumn_identifier(),ts.getId_customer());
                idSeatMap.put(ts.getRow_identifier() +" "+ ts.getColumn_identifier(),ts.getId_seat());
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
                }else if(tabART[i-1][j]=='R'){
                    button.setStyle("-fx-background-color: #ffd500");
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

                            if(tabART[idRow][idCol]=='R'){
                                idClientLabel.setText("Id klienta: " + Long.toString(idCustomerMap.get(((Button)actionEvent.getSource()).getText())));
                            }else{
                                idClientLabel.setText("Brak");
                            }

                        }else{
                            if(tabART[idRow][idCol]=='A'){
                                button.setStyle("-fx-background-color: #00ff00");
                            }else if(tabART[idRow][idCol]=='R') {
                                button.setStyle("-fx-background-color: #ffd500");
                            }
                            tabIsChoose[idRow][idCol]=false;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCustomerMap = new HashMap<>();
        idSeatMap = new HashMap<>();
        buildSeats();

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,row*col/2);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,row*col/2);
        valueFactory1.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                costLabel.setText("Koszt: " + calculatePrice() + " zł");
            }
        });

        valueFactory2.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                costLabel.setText("Koszt: " + calculatePrice() + " zł");
            }
        });

        valueFactory1.setValue(0);
        valueFactory2.setValue(0);

        normalTicketSpinner.setValueFactory(valueFactory1);
        reducedTicketSpinner.setValueFactory(valueFactory2);
    }
}
