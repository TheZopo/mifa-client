package fr.mifa.client.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUIManager {
    private Stage stage;
    private Scene currentScene;

    public GUIManager(Stage stage) {
        this.stage = stage;
        init();
    }

    public void init() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        currentScene = new Scene(new StackPane(label), 1280, 720);

        stage.setTitle("mIFA Client");
        stage.setScene(currentScene);
        stage.show();
    }

}
