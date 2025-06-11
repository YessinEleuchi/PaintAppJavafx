package com.example.demo1.model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation concrète d'un sujet observable qui gère une liste d'observateurs.
 * Notifie tous les observateurs en cas de changement.
 */
public class ObservableImpl implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (o != null) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        // Créer une copie pour éviter les problèmes de modification pendant la notification
        List<Observer> observersCopy = new ArrayList<>(observers);
        for (Observer o : observersCopy) {
            if (o != null) {
                o.update(arg);
            }
        }
    }
}