package com.example.cinema;

import DTO.MovieAndSeance;
import Database.DatabaseConnection;

public class Model {

    private long id = 0;
    private String login = "";

    private static MovieAndSeance movieAndSeance;

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

    private final DatabaseConnection database;

    public Model(DatabaseConnection databaseConnection) {
        database = databaseConnection;
    }

    public DatabaseConnection getDatabase() {
        return database;
    }

}
