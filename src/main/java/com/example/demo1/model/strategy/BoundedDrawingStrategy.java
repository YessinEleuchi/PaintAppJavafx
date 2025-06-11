package com.example.demo1.model.strategy;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Stratégie qui limite les interactions à l'intérieur des limites fixes de la pane de dessin (800x550).
 */
public class BoundedDrawingStrategy implements DrawingConstraintStrategy {
    private static final double MAX_WIDTH = 800;
    private static final double MAX_HEIGHT = 550;

    @Override
    public double constrainX(MouseEvent event, Pane pane) {
        double x = event.getX();
        if (x < 0) return 0;
        if (x > MAX_WIDTH) return MAX_WIDTH;
        return x;
    }

    @Override
    public double constrainY(MouseEvent event, Pane pane) {
        double y = event.getY();
        if (y < 0) return 0;
        if (y > MAX_HEIGHT) return MAX_HEIGHT;
        return y;
    }
}