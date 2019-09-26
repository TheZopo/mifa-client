package fr.mifa.client.gui;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

import fr.mifa.client.MIFAClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIManager {
    private static final Logger logger = LoggerFactory.getLogger(GUIManager.class);

    private Stage stage;
    private Scene currentScene;

    private HashMap<String, Scene> scenes;

    public GUIManager(Stage stage) {
        this.stage = stage;
        this.scenes = new HashMap<>();

        init();
    }

    public void init() {
        loadAllScenes();
        currentScene = scenes.get("chat");

        stage.setTitle("mIFA Client");
        stage.setScene(currentScene);
        stage.show();
    }

    public void loadAllScenes() {
        Set<String> viewsPaths = new Reflections("views", new ResourcesScanner()).getResources(Pattern.compile(".*"));
        for(String viewPath : viewsPaths) {
            loadScene(viewPath);
        }

    }

    public void loadScene(String resourcePath) {
        String sceneName = Paths.get(resourcePath).getFileName().toString().split(".fxml")[0];
        if(scenes.containsKey(sceneName)) return;

        FXMLLoader loader = new FXMLLoader();
        loader.setCharset(Charset.forName("UTF-8"));
        try {
            Scene scene = new Scene(loader.load(MIFAClient.class.getResourceAsStream("/" + resourcePath)), 1280, 720);
            scene.getStylesheets().addAll(
                    getClass().getResource("/css/components.css").toExternalForm(),
                    getClass().getResource("/css/style.css").toExternalForm()
            );
            scenes.put(sceneName, scene);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void changeScene(String sceneName) {
        if(scenes.containsKey(sceneName)) {
            currentScene = scenes.get(sceneName);
            stage.setScene(currentScene);
        } else {
           logger.error("The scene \"" + sceneName + "\" doesn't exists !");
        }
    }
}
