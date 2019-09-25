package fr.mifa.client.services;

import fr.mifa.core.models.Message;
import fr.mifa.core.models.Room;
import fr.mifa.core.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public enum RoomService {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    ArrayList<Room> rooms;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    RoomService() {
        this.rooms = new ArrayList<>();
    }

    public void userJoined(int roomId, String user) {
        Room room = getRoomById(roomId);
        room.getUsers().add(new User(user, null));
    }

    public void userLeft(int roomId, String user) {
        Room room = getRoomById(roomId);
        room.getUsers().remove(room.getUsers()
                .stream()
                .filter(u -> user.equals(u.getNickname()))
                .findFirst()
                .get()
        );
    }

    public void messageSent(Message message) {
        Room room = getRoomByName(message.getRoomName());
        room.getHistory().add(message);
    }

    private Room getRoomByName(String roomName) {
        return this.rooms.stream()
                .filter(r -> roomName.equals(r.getName()))
                .findFirst()
                .get();
    }

    private Room getRoomById(int roomId) {
        return this.rooms.stream()
                .filter(r -> roomId == r.getId())
                .findFirst()
                .get();
    }
}
