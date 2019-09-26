package fr.mifa.client.services;

import fr.mifa.core.models.Room;
import fr.mifa.core.models.TextMessage;
import fr.mifa.core.network.protocol.MessagePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum  UserService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
        }
    }

    public void sendText(String text) {
        if (currentRoom != null) {
            NetworkService.INSTANCE.sendPacket(new MessagePacket(new TextMessage(
                    currentRoom.getName(),
                    text
            )));
        }
        else {
            logger.warn("You're not in a room right now ; can't send a message");
        }
    }
}
