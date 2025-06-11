package com.example.demo1.model.observer;

/**
 * Interface fonctionnelle définissant un observateur qui réagit à des notifications.
 * Les implémentations doivent mettre à jour leur état en réponse à un évènement.
 */
@FunctionalInterface
public interface Observer {
    /**
     * Met à jour l'état de l'observateur en réponse à un évènement.
     * @param arg les informations sur l'évènement (par exemple, un message)
     */
    void update(Object arg);
}