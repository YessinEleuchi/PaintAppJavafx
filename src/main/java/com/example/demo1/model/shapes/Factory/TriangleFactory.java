package com.example.demo1.model.shapes.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleFactory implements ShapeFactory {

    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, double paneWidth, double paneHeight) {
        // 🔒 Clamp les coordonnées pour rester dans la zone visible
        double baseX1 = Math.max(0, Math.min(startX, paneWidth));
        double baseY1 = Math.max(0, Math.min(startY, paneHeight));
        double baseX2 = Math.max(0, Math.min(endX, paneWidth));
        double baseY2 = Math.max(0, Math.min(endY, paneHeight));

        // Milieu de la base
        double midX = (baseX1 + baseX2) / 2;
        double midY = (baseY1 + baseY2) / 2;

        // Longueur de la base
        double dx = baseX2 - baseX1;
        double dy = baseY2 - baseY1;
        double baseLength = Math.hypot(dx, dy);

        // 🔼 Hauteur du triangle (perpendiculaire à la base)
        double height = baseLength / 1.5; // réglable
        double normX = -dy / baseLength;
        double normY = dx / baseLength;

        // Calcul du sommet du triangle (hors de la base)
        double apexX = midX + normX * height;
        double apexY = midY + normY * height;

        // Clamp sommet aussi dans la zone de dessin
        apexX = Math.max(0, Math.min(apexX, paneWidth));
        apexY = Math.max(0, Math.min(apexY, paneHeight));

        Polygon triangle = new Polygon(
                baseX1, baseY1,
                baseX2, baseY2,
                apexX, apexY
        );

        triangle.setFill(Color.TRANSPARENT);
        triangle.setStroke(Color.BLACK);
        return triangle;
    }
}