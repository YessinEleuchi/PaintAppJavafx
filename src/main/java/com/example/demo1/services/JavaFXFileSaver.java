package com.example.demo1.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.List;



public class JavaFXFileSaver implements FileSaver {
    private final WindowProvider windowProvider;

    public JavaFXFileSaver(WindowProvider windowProvider) {
        this.windowProvider = windowProvider;
    }

    @Override
    public File saveTextFile(List<String> lines) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Exporter les logs");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texte", "*.txt"));
        Window window = windowProvider.getWindow();
        File file = chooser.showSaveDialog(window);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        return file;
    }

    @Override
    public File saveImageFile(WritableImage image) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Enregistrer l'image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image PNG", "*.png"));
        Window window = windowProvider.getWindow();
        File file = chooser.showSaveDialog(window);
        if (file != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
        return file;
    }
}
