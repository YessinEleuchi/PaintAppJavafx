package com.example.demo1.model.strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogStrategy implements LogStrategy {
    private final String filePath = "logs.txt"; // Chemin du fichier

    @Override
    public void log(String message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(LocalDateTime.now() + " [LOG File] " + message + "\n");
        } catch (IOException e) {
            System.err.println("Erreur d’écriture dans le fichier de log : " + e.getMessage());
        }
    }
}
