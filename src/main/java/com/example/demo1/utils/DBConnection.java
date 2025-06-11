package com.example.demo1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/draw_app?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // mot de passe MySQL si défini

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL chargé avec succès.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Impossible de charger le driver MySQL : " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}