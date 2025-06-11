package com.example.demo1.model.Decorator;

import javafx.scene.shape.Shape;

/**
 * Classe abstraite de base pour les décorateurs de formes, permettant d'ajouter des fonctionnalités
 * à une forme existante sans modifier sa classe originale.
 */
public abstract class ShapeDecorator {
    protected final Shape decoratedShape; // Utilisation de 'final' pour l'immutabilité

    /**
     * Constructeur qui initialise la forme à décorer.
     * @param decoratedShape la forme à décorer, ne doit pas être null
     * @throws IllegalArgumentException si decoratedShape est null
     */
    public ShapeDecorator(Shape decoratedShape) {
        if (decoratedShape == null) {
            throw new IllegalArgumentException("La forme à décorer ne peut pas être null.");
        }
        this.decoratedShape = decoratedShape;
    }

    /**
     * Retourne la forme décorée.
     * @return la forme encapsulée
     */
    public Shape getDecoratedShape() {
        return decoratedShape;
    }

    /**
     * Applique les décorations à la forme.
     * À implémenter par les sous-classes.
     */
    protected abstract void apply();
}