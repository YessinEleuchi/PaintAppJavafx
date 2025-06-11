package com.example.demo1.model.shapes.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight) {
        double dx = endX - startX;
        double dy = endY - startY;
        double radius = Math.hypot(dx, dy);

        double maxRadiusX = Math.min(startX, paneWidth - startX);
        double maxRadiusY = Math.min(startY, paneHeight - startY);
        double maxRadius = Math.min(maxRadiusX, maxRadiusY);

        radius = Math.min(radius, maxRadius);

        Circle circle = new Circle(startX, startY, radius);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        return circle;
    }

}
