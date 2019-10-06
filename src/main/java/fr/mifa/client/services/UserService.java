package fr.mifa.client.services;

import fr.mifa.client.gui.controllers.ChatController;
import fr.mifa.client.gui.controls.MessageType;
import fr.mifa.core.models.FileMessage;
import fr.mifa.core.models.Message;
import fr.mifa.core.models.Room;
import fr.mifa.core.models.TextMessage;
import fr.mifa.core.network.protocol.MessagePacket;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public enum  UserService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final int UDP_SIZE_LIMIT = 65000;

    private Room currentRoom;

    private String userNickname;

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        if (currentRoom != this.currentRoom) {
            this.currentRoom = currentRoom;
            Platform.runLater(() -> {
                ChatController.INSTANCE.switchToRoom(currentRoom);
            });
        }
    }

    public void sendText(String text) {
        if (currentRoom != null) {
            currentRoom.getPacketManager().send(new MessagePacket(new TextMessage(
                    currentRoom.getName(),
                    userNickname,
                    text
            )));
        }
        else {
            logger.warn("You're not in a room right now ; can't send a message");
        }
    }

    public void sendFile(File file) {
        try {
            byte[] content = Files.readAllBytes(file.toPath());
            if (content.length >= UDP_SIZE_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot send message");
                alert.setContentText("Your file is too big to be uploaded on mIFA directly - use a file sharing website !");

                alert.show();
                return;
            }
            if (currentRoom != null) {
                currentRoom.getPacketManager().send(new MessagePacket(new FileMessage(
                        currentRoom.getName(),
                        userNickname,
                        content,
                        file.getName()
                )));
            }
            else {
                logger.warn("You're not in a room right now ; can't send a message");
            }
        }
        catch (IOException ex) {
            logger.error(ex.toString());
        }
    }
}
