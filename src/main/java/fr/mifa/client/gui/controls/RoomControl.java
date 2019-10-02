package fr.mifa.client.gui.controls;

import fr.mifa.client.services.UserService;
import fr.mifa.core.models.Room;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

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
        this.setOnMouseClicked((EventHandler<? super MouseEvent>) e -> onClick());
    }

    @FXML
    public void initialize() {
        roomName.setText(room.getName());
    }

    private void onClick() {
        UserService.INSTANCE.setCurrentRoom(room);
    }
}
