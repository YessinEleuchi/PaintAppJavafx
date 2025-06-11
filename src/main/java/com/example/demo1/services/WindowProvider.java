package com.example.demo1.services;

import javafx.stage.Window;

@FunctionalInterface
public interface WindowProvider {
    Window getWindow();
}
