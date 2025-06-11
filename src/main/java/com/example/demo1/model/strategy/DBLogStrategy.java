package com.example.demo1.model.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBLogStrategy implements LogStrategy {
    private final Connection connection;

    public DBLogStrategy(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void log(String message) {
        if (connection == null) {
            System.err.println("❌ Connexion indisponible. Log non effectué.");
            return;
        }

        String sql = "INSERT INTO logs (message, log_time) VALUES (?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.executeUpdate();
            System.out.println("✅ Log inséré en base : " + message);
        } catch (SQLException e) {
            System.err.println("❌ Erreur DBLogStrategy : " + e.getMessage());
        }
    }
}
