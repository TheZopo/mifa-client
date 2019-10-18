package fr.mifa.client.services;

import fr.mifa.client.gui.controllers.ChatController;
import fr.mifa.client.gui.controls.MessageType;
import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.client.network.RoomClientPacketManager;
import fr.mifa.core.models.Message;
import fr.mifa.core.models.Room;
import fr.mifa.core.network.protocol.JoinRoomPacket;
import fr.mifa.core.network.protocol.LeaveRoomPacket;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public enum RoomService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    ArrayList<Room> rooms;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        for(Room room : rooms) {
            room.setPacketManager(new RoomClientPacketManager(room));
        }

        this.rooms = rooms;
        syncView();
    }

    RoomService() {
        this.rooms = new ArrayList<>();
    }

    public void userJoined(Room room, String user) {
        Optional<Room> storedRoom = getRoomById(room.getId());
        storedRoom.ifPresent(value -> this.rooms.remove(value));

        room.setPacketManager(new RoomClientPacketManager(room));
        logger.debug("history size" + room.getHistory().size());
        this.rooms.add(room);
        UserService.INSTANCE.setCurrentRoom(room);

        syncView();
    }

    public void userLeft(int roomId, String user) {
        Optional<Room> room = getRoomById(roomId);
        if (user.equals(UserService.INSTANCE.getUserNickname()) && room.isPresent()) {
            this.rooms.remove(room.get());
            UserService.INSTANCE.setCurrentRoom(null);
            syncView();
        }
        else {
            room.ifPresent(value -> value.getUsers().remove(
                    value.getUsers()
                            .stream()
                            .filter(u -> user.equals(u.getNickname()))
                            .findFirst()
                            .get()
            ));
        }
    }

    public void joinRoom(String room) {
        NetworkService.INSTANCE.sendPacket(new JoinRoomPacket(room));
    }

    public void leaveRoom(Room room) {
        NetworkService.INSTANCE.sendPacket(new LeaveRoomPacket(room.getName()));
        room.getPacketManager().disconnect();
    }

    public void messageSent(Message message) {
        Optional<Room> room = getRoomByName(message.getRoomName());
        if (room.isPresent()) {
            room.get().getHistory().add(message);
            MessageType origin = message.getAuthorName().equals(UserService.INSTANCE.getUserNickname()) ? MessageType.ME : MessageType.OTHER;
            if (room.get().getName().equals(UserService.INSTANCE.getCurrentRoom().getName())) {
                Platform.runLater(() -> {
                    ChatController.INSTANCE.getCurrentRoomControl().addMessage(message, origin);
                });
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
}
