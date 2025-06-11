package com.example.demo1.model.shapes.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight) {
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        width = Math.min(width, paneWidth - x);
        height = Math.min(height, paneHeight - y);

        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        return rect;
    }

}
