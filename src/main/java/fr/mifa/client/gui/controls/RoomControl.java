package fr.mifa.client.gui.controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import fr.mifa.core.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomControl extends HBox {
    private Room room;

    @FXML
    private Label roomName;

    public RoomControl(Room room) {
        this.room = room;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(getClass().getResourceAsStream("/controls/RoomControl.fxml"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //TODO onMouseClicked -> change room
    }

    @FXML
    public void initialize() {
        roomName.setText(room.getName());
    }
}
