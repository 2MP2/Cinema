package com.example.cinema;

import Controller.DatabaseConnection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Model {
    private final ObjectProperty<DatabaseConnection> database = new SimpleObjectProperty<>(null);

    public Model(DatabaseConnection databaseConnection) {
        database.set(databaseConnection);
    }

    public DatabaseConnection getDatabase() {
        return database.get();
    }

    public ObjectProperty<DatabaseConnection> databaseProperty() {
        return database;
    }


}
