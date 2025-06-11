package com.example.demo1.model.Decorator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Décorateur qui ajoute une bordure à une forme avec une couleur et une épaisseur.
 */
public class BorderDecorator extends ShapeDecorator {
    private final Color strokeColor;
    private final double strokeWidth;

    /**
     * Constructeur qui initialise la bordure avec une couleur et une épaisseur.
     * @param decoratedShape la forme à décorer
     * @param strokeColor la couleur de la bordure
     * @param strokeWidth l'épaisseur de la bordure
     */
    public BorderDecorator(Shape decoratedShape, Color strokeColor, double strokeWidth) {
        super(decoratedShape);
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        apply();
    }

    @Override
    protected void apply() {
        decoratedShape.setStroke(strokeColor);
        decoratedShape.setStrokeWidth(strokeWidth);
    }
}