package ru.sof_ya.demo;

import ru.sof_ya.demo.ui.ProductTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        new ProductTableModel();
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demoexam2", "root", "friday.naw");
    }
}
