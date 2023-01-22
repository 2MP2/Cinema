package com.example.cinema;

import DTO.MovieAndSeance;
import Database.DatabaseConnection;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Model {

    private long id = 0;
    private String login = "";
    private final DatabaseConnection database;

    private static MovieAndSeance movieAndSeance;
    public static Scene stage;

    public static MovieAndSeance getMovieAndSeance() {
        return movieAndSeance;
    }

    public static void setMovieAndSeance(MovieAndSeance movieAndSeance) {
        Model.movieAndSeance = movieAndSeance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void clear(){
        id = 0;
        login ="";
    }

    public Model(DatabaseConnection databaseConnection) {
        database = databaseConnection;
    }

    public DatabaseConnection getDatabase() {
        return database;
    }

}
