package com.example.demo1.model.shapes.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class FleshFactory implements ShapeFactory {

    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight) {
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        double radiusX = Math.abs(endX - startX) / 2;
        double radiusY = Math.abs(endY - startY) / 2;

        // 🔒 Clamp des rayons pour rester dans la zone visible
        radiusX = Math.min(radiusX, Math.min(centerX, paneWidth - centerX));
        radiusY = Math.min(radiusY, Math.min(centerY, paneHeight - centerY));

        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.BLACK);
        return ellipse;
    }

}
