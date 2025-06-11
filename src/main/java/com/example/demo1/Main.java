package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 650); // Grande taille
        scene.getStylesheets().add(getClass().getResource("/com/example/demo1/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("MASI – Mini Projet JavaFX");
        stage.setMinWidth(900);
        stage.setMinHeight(550);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
