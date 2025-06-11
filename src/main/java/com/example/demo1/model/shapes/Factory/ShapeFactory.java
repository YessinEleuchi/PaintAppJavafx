package com.example.demo1.model.shapes.Factory;

import javafx.scene.shape.Shape;

public interface ShapeFactory {
    Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight);
}

