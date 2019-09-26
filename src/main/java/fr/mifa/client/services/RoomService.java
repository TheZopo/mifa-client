package fr.mifa.client.services;

import fr.mifa.client.gui.controllers.ChatController;
import fr.mifa.client.gui.controls.MessageType;
import fr.mifa.core.models.Message;
import fr.mifa.core.models.Room;
import fr.mifa.core.models.TextMessage;
import fr.mifa.core.models.User;
import fr.mifa.core.network.protocol.JoinRoomPacket;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public enum RoomService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private ArrayList<Room> rooms;
    private String nickname;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
        syncView();
    }

    RoomService() {
        this.rooms = new ArrayList<>();
    }

    public void userJoined(Room room, String user) {
        Optional<Room> storedRoom = getRoomById(room.getId());
        storedRoom.ifPresent(value -> this.rooms.remove(value));
        this.rooms.add(room);
        syncView();
    }

    public void userLeft(int roomId, String user) {
        Optional<Room> room = getRoomById(roomId);
        room.ifPresent(value -> value.getUsers().remove(
                value.getUsers()
                        .stream()
                        .filter(u -> user.equals(u.getNickname()))
                        .findFirst()
                        .get()
        ));
    }

    public void joinRoom(String room) {
        NetworkService.INSTANCE.sendPacket(new JoinRoomPacket(room));
        //TODO: load messages & set room name
    }

    public void messageSent(Message message) {
        Optional<Room> room = getRoomByName(message.getRoomName());
        if (room.isPresent()) {
            room.get().getHistory().add(message);

            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                MessageType messageType = textMessage.getAuthorName().equals(nickname) ? MessageType.ME : MessageType.OTHER;
                ChatController.INSTANCE.addMessage(textMessage.getAuthorName(), textMessage.getText(), messageType);
            }
        }
        else {
            logger.warn("Room does not exist !");
        }
    }

    private Optional<Room> getRoomByName(String roomName) {
        return this.rooms.stream()
                .filter(r -> roomName.equals(r.getName()))
                .findFirst();
    }

    private Optional<Room> getRoomById(int roomId) {
        return this.rooms.stream()
                .filter(r -> roomId == r.getId())
                .findFirst();
    }

    private void syncView() {
        Platform.runLater(() -> {
            ChatController.INSTANCE.loadRooms(rooms);
        });
    }

    public void registerNickname(String nickname) {
        this.nickname = nickname;
    }
}
