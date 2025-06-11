package com.example.demo1.model.command;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import java.util.function.Consumer;

public class AddShapeCommand implements Command {
    private final Pane drawingPane;
    private final Shape shape;
    private final Consumer<String> logAction;

    public AddShapeCommand(Pane drawingPane, Shape shape, Consumer<String> logAction) {
        this.drawingPane = drawingPane;
        this.shape = shape;
        this.logAction = logAction;
    }

    @Override
    public void execute() {
        drawingPane.getChildren().add(shape);
        logAction.accept("Forme ajoutée.");
    }

    @Override
    public void undo() {
        if (drawingPane.getChildren().remove(shape)) {
            logAction.accept("Forme supprimée (annulée).");
        } else {
            logAction.accept("Échec de l'annulation : forme non trouvée.");
        }
    }
}