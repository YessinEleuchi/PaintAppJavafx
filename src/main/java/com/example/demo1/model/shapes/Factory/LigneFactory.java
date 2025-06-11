package com.example.demo1.model.shapes.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LigneFactory implements ShapeFactory {

    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight) {
        endX = Math.max(0, Math.min(endX, paneWidth));
        endY = Math.max(0, Math.min(endY, paneHeight));

        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLACK);
        return line;
    }

}
