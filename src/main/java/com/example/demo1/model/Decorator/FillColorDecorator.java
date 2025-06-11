package com.example.demo1.model.Decorator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Décorateur qui applique une couleur de remplissage à une forme.
 */
public class FillColorDecorator extends ShapeDecorator {
    private final Color fillColor;

    /**
     * Constructeur qui initialise la couleur de remplissage.
     * @param decoratedShape la forme à décorer
     * @param fillColor la couleur de remplissage
     */
    public FillColorDecorator(Shape decoratedShape, Color fillColor) {
        super(decoratedShape);
        this.fillColor = fillColor;
        apply();
    }

    @Override
    protected void apply() {
        decoratedShape.setFill(fillColor);
    }
}