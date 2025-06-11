package com.example.demo1.model.command;

import com.example.demo1.services.FileSaver;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class SaveCommand implements Command {
    private final Pane drawingPane;
    private final FileSaver fileSaver;
    private final Consumer<String> logAction;
    private File savedFile;

    public SaveCommand(Pane drawingPane, FileSaver fileSaver, Consumer<String> logAction) {
        this.drawingPane = drawingPane;
        this.fileSaver = fileSaver;
        this.logAction = logAction;
    }

    @Override
    public void execute() {
        WritableImage image = drawingPane.snapshot(new SnapshotParameters(), null);
        try {
            savedFile = fileSaver.saveImageFile(image);
            if (savedFile != null) {
                logAction.accept("Dessin sauvegardé dans : " + savedFile.getAbsolutePath());
            } else {
                logAction.accept("Sauvegarde annulée.");
            }
        } catch (IOException e) {
            logAction.accept("Erreur de sauvegarde : " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        if (savedFile != null && savedFile.exists()) {
            if (savedFile.delete()) {
                logAction.accept("Sauvegarde annulée : " + savedFile.getAbsolutePath() + " supprimé.");
            } else {
                logAction.accept("Échec de l'annulation : impossible de supprimer " + savedFile.getAbsolutePath());
            }
        } else {
            logAction.accept("Aucune action à annuler.");
        }
    }
}
