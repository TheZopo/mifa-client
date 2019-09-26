package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;

import fr.mifa.client.gui.controls.MessageControl;
import fr.mifa.client.gui.controls.MessageType;
import fr.mifa.client.gui.controls.RoomControl;
import fr.mifa.client.services.NetworkService;
import fr.mifa.client.services.RoomService;
import fr.mifa.core.models.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatController {
    public static ChatController INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @FXML
    JFXTextField roomToAdd;

    @FXML
    VBox roomList;

    @FXML
    VBox messagesList;

    @FXML
    JFXTextArea message;

    @FXML
    Label roomName;

    @FXML
    ScrollPane scrollPane;

    public ChatController() {
        INSTANCE = this;
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(messagesList.heightProperty());
        //TODO: load messages
    }

    @FXML
    public void sendMessage(MouseEvent mouseEvent) {
        if(message.validate()) {
            NetworkService.INSTANCE.sendTextMessage("room", message.getText());
            message.clear();
        }
    }

    @FXML
    public void addRoom(MouseEvent mouseEvent) {
        logger.debug("Adding room");
        RoomService.INSTANCE.joinRoom(roomToAdd.getText());
    }

    @FXML
    public void changeRoom(MouseEvent mouseEvent) {
        logger.debug("Changing room");
    }

    public void loadRooms(ArrayList<Room> rooms) {
        roomList.getChildren().clear();
        for (Room room : rooms) {
            roomList.getChildren().add(new RoomControl(room));
        }
    }

    //TODO: naming
    public void addMessage(String author, String message, MessageType messageType) {
        Platform.runLater(() -> {
            MessageControl lastMessage = messagesList.getChildren().size() > 0 ? (MessageControl) messagesList.getChildren().get(messagesList.getChildren().size() - 1) : null;

            if(lastMessage != null && lastMessage.getAuthorName().equals(author)) {
                lastMessage.addMessage(message);
            } else {
                ArrayList<String> subMessagesList = new ArrayList<>();
                subMessagesList.add(message);

                messagesList.getChildren().add(new MessageControl(author, subMessagesList, messageType));
            }
        });
    }
}
