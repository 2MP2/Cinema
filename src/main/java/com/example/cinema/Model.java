package com.example.cinema;

import Controller.DatabaseConnection;
import Model.Customers;
import Model.Employees;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Model {

    private Customers currentCustomer;

    public Customers getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customers currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Employees getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employees currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    private Employees currentEmployee;
    private DatabaseConnection database;

    public Model(DatabaseConnection databaseConnection) {
        database = databaseConnection;
    }

    public DatabaseConnection getDatabase() {
        return database;
    }

}
