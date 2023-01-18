package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Model {
    private DatabaseConnection database;

    public Model(DatabaseConnection databaseConnection) {
        database = databaseConnection;
    }

    public DatabaseConnection getDatabase() {
        return database;
    }

}
