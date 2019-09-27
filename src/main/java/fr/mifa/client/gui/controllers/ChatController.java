package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import fr.mifa.client.gui.controls.ChatroomControl;
import fr.mifa.client.gui.controls.RoomControl;
import fr.mifa.client.services.RoomService;
import fr.mifa.client.services.UserService;
import fr.mifa.core.models.Room;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ChatController {
    public static ChatController INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @FXML
    JFXTextField roomToAdd;

    @FXML
    VBox roomList;

    @FXML
    GridPane container;

    private ChatroomControl currentRoomControl;

    public ChatController() {
        INSTANCE = this;
    }

    @FXML
    public void initialize() {
        /*ArrayList<String> messages = new ArrayList<>();
        messages.add("Hello");
        messages.add("Hello my dear");
        messages.add("Hello my dear how are you ?");

        messagesList.getChildren().add(new MessageControl("Bastien", messages, MessageType.OTHER));

        messages.clear();
        messages.add("Hi !");
        messages.add("Hi ! I'm fine");
        messages.add("Hi ! I'm fine thanks you !");

        messagesList.getChildren().add(new MessageControl("Me", messages, MessageType.ME));
        */

        //TODO: load messages
    }

    public ChatroomControl getCurrentRoomControl() {
        return currentRoomControl;
    }

    @FXML
    public void addRoom(MouseEvent mouseEvent) {
        if (roomToAdd.validate()) {
            RoomService.INSTANCE.joinRoom(roomToAdd.getText());
            roomToAdd.setText("");
        }
    }

    public void loadRooms(ArrayList<Room> rooms) {
        roomList.getChildren().clear();
        logger.debug("Received "  + rooms.size() + " rooms");
        for (Room room : rooms) {
            roomList.getChildren().add(new RoomControl(room));
        }
        Room currentRoom = UserService.INSTANCE.getCurrentRoom();
        switchToRoom(currentRoom);
    }

    public void switchToRoom(Room room) {
        ObservableList<Node> children = container.getChildren();
        if (children.size() == 2) {
            // a room is already loaded, remove it to replace
            children.remove(container.getChildren().size() - 1);
        }
        if (room != null) {
            currentRoomControl = new ChatroomControl(room);
            children.add(currentRoomControl);
        }
    }
}
