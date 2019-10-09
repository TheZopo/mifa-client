package fr.mifa.client.network;

import fr.mifa.client.services.RoomService;
import fr.mifa.core.models.Room;
import fr.mifa.core.network.RoomPacketManager;
import fr.mifa.core.network.protocol.MessagePacket;
import fr.mifa.core.network.protocol.Packet;

public class RoomClientPacketManager extends RoomPacketManager {
    public RoomClientPacketManager(Room room) {
        super(room.getAddress(), room.getPort());
    }

    @Override
    protected void processPacket(Packet packet) {
        if(packet instanceof MessagePacket) {
            MessagePacket messagePacket = (MessagePacket) packet;
            RoomService.INSTANCE.messageSent(messagePacket.getMessage());
        }
    }
}