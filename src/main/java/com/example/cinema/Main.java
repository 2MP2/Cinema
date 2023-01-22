package com.example.cinema;

import Database.DatabaseConnection;
import Test.TestReczny;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@DESKTOP-NJJMCEP:1521:xe","c##cinema_user","cinema_user");
        Model model = new Model(databaseConnection);
        Parent root;

        StartController.setModel(model);

        RegistrationController.setModel(model);
        LoginController.setModel(model);
        MainCustomerController.setModel(model);
        MainEmployeeController.setModel(model);

        EmployeeAddEmployeeController.setModel(model);
        EmployeeAddFilmController.setModel(model);
        EmployeeAddSeancesController.setModel(model);
        EmployeeEditEmployeeController.setModel(model);
        EmployeeFireEmployeeController.setModel(model);
        EmployeeSeancesController.setModel(model);

        CustomerBuyController.setModel(model);
        CustomerEditCustomerController.setModel(model);
        CustomerMyTicketController.setModel(model);

        FXMLLoader starViewLoader = new FXMLLoader(Main.class.getResource("StartView.fxml"));
        root = starViewLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Kino");
        stage.setResizable(false);
        Image logo = new Image("C:\\Users\\48602\\Desktop\\Sem 5\\BazyDanych2\\Kod\\logo.png");
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        TestReczny testReczny = new TestReczny();
        testReczny.test();
        launch();
    }
}