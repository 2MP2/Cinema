package com.example.cinema;

import Controller.DatabaseConnection;
import Test.TestReczny;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@DESKTOP-NJJMCEP:1521:xe","c##cinema_user","cinema_user");
        Model model = new Model(databaseConnection);
        Parent root;


        FXMLLoader registrationViewLoader = new FXMLLoader(Main.class.getResource("RegistrationView.fxml"));
        root = registrationViewLoader.load();
        RegistrationController registrationController = registrationViewLoader.getController();
        registrationController.initModel(model);

        FXMLLoader loginViewLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        root = loginViewLoader.load();
        LoginController loginController = loginViewLoader.getController();
        loginController.initModel(model);

        FXMLLoader starViewLoader = new FXMLLoader(Main.class.getResource("StartView.fxml"));
        root = starViewLoader.load();
        StartController startController = starViewLoader.getController();
        startController.initModel(model);

        Scene scene = new Scene(root);
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