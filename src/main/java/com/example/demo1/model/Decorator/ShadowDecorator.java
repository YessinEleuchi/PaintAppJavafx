package com.example.demo1.model.Decorator;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Décorateur qui ajoute une ombre à une forme.
 */
public class ShadowDecorator extends ShapeDecorator {
    private final Color shadowColor;
    private final double radius;

    /**
     * Constructeur qui initialise l'ombre avec une couleur et un rayon.
     * @param decoratedShape la forme à décorer
     * @param shadowColor la couleur de l'ombre
     * @param radius le rayon de l'ombre
     */
    public ShadowDecorator(Shape decoratedShape, Color shadowColor, double radius) {
        super(decoratedShape);
        this.shadowColor = shadowColor;
        this.radius = radius;
        apply();
    }

    @Override
    protected void apply() {
        decoratedShape.setEffect(new DropShadow(radius, shadowColor));
    }
}