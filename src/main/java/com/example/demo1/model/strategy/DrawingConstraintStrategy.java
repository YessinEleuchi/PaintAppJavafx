package com.example.demo1.model.strategy;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Interface pour les stratégies de contrainte de la zone de dessin.
 */
public interface DrawingConstraintStrategy {
    /**
     * Applique une contrainte à l'événement de la souris en fonction des limites de la pane.
     * @param event l'événement de la souris
     * @param pane la pane de dessin
     * @return les coordonnées x ajustées, ou -1 si l'événement doit être ignoré
     */
    double constrainX(MouseEvent event, Pane pane);

    /**
     * Applique une contrainte à l'événement de la souris en fonction des limites de la pane.
     * @param event l'événement de la souris
     * @param pane la pane de dessin
     * @return les coordonnées y ajustées, ou -1 si l'événement doit être ignoré
     */
    double constrainY(MouseEvent event, Pane pane);
}