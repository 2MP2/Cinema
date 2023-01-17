package com.example.cinema;

import Controller.DatabaseConnection;
import Test.TestReczny;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@192.168.1.58:1521:xe","c##cinema_user","cinema_user");
        //Model model = new Model(databaseConnection);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kino");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        TestReczny testReczny = new TestReczny();
        testReczny.test();
        launch();
    }
}