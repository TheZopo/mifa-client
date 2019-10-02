package fr.mifa.client;

import fr.mifa.client.gui.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MIFAClient extends Application {
    public static GUIManager guiManager;

    public static void main(String[] args) {
        System.out.println("Hello World mifa-client !");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        guiManager = new GUIManager(stage);
    }
}
