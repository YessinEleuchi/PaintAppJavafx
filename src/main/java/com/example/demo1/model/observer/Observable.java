package com.example.demo1.model.observer;

/**
 * Interface définissant les méthodes pour gérer les observateurs d'un sujet observable.
 * Permet d'ajouter, retirer et notifier les observateurs d'un changement.
 */
public interface Observable {
    /**
     * Ajoute un observateur à la liste des observateurs, si ce dernier n'est pas déjà présent.
     * @param o l'observateur à ajouter
     */
    void addObserver(Observer o);

    /**
     * Retire un observateur de la liste des observateurs.
     * @param o l'observateur à retirer
     */
    void removeObserver(Observer o);

    /**
     * Notifie tous les observateurs d'un changement avec un argument spécifique.
     * @param arg l'objet contenant les informations sur l'évènement
     */
    void notifyObservers(Object arg);
}