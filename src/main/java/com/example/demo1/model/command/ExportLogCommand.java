package com.example.demo1.model.command;

import com.example.demo1.services.FileSaver;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ExportLogCommand implements Command {
    private final ListView<String> logListView;
    private final FileSaver fileSaver;
    private final Consumer<String> logAction;
    private File exportedFile;

    public ExportLogCommand(ListView<String> logListView, FileSaver fileSaver, Consumer<String> logAction) {
        this.logListView = logListView;
        this.fileSaver = fileSaver;
        this.logAction = logAction;
    }

    @Override
    public void execute() {
        try {
            ObservableList<String> logs = logListView.getItems();
            exportedFile = fileSaver.saveTextFile(logs);
            if (exportedFile != null) {
                logAction.accept("Logs exportés dans : " + exportedFile.getAbsolutePath());
            } else {
                logAction.accept("Exportation annulée.");
            }
        } catch (IOException e) {
            logAction.accept("Erreur d'exportation : " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        if (exportedFile != null && exportedFile.exists()) {
            if (exportedFile.delete()) {
                logAction.accept("Exportation annulée : " + exportedFile.getAbsolutePath() + " supprimé.");
            } else {
                logAction.accept("Échec de l'annulation : impossible de supprimer " + exportedFile.getAbsolutePath());
            }
        } else {
            logAction.accept("Aucune action à annuler.");
        }
    }
}
