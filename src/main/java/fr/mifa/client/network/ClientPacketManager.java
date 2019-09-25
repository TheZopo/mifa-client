package fr.mifa.client.network;

import fr.mifa.client.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mifa.core.network.PacketManager;
import fr.mifa.core.network.protocol.HelloWorldPacket;
import fr.mifa.core.network.protocol.JoinedRoomPacket;
import fr.mifa.core.network.protocol.LeftRoomPacket;
import fr.mifa.core.network.protocol.MessageSentPacket;
import fr.mifa.core.network.protocol.Packet;
import fr.mifa.core.network.protocol.RoomListPacket;

public class ClientPacketManager extends PacketManager {
    private static final Logger logger = LoggerFactory.getLogger(ClientPacketManager.class);

    @Override
    protected void processPacket(Packet packet) {
        if(packet instanceof JoinedRoomPacket) {
            JoinedRoomPacket joinedRoomPacket = (JoinedRoomPacket) packet;
            RoomService.INSTANCE.userLeft(joinedRoomPacket.getRoomId(), joinedRoomPacket.getNickname());
        } else if(packet instanceof LeftRoomPacket) {
            LeftRoomPacket leftRoomPacket = (LeftRoomPacket) packet;
            RoomService.INSTANCE.userLeft(leftRoomPacket.getRoomId(), leftRoomPacket.getNickname());
        } else if(packet instanceof MessageSentPacket) {
            MessageSentPacket messageSentPacket = (MessageSentPacket) packet;
            RoomService.INSTANCE.messageSent(messageSentPacket.getMessage());
        } else if(packet instanceof RoomListPacket) {
            RoomListPacket roomListPacket = (RoomListPacket)packet;
            RoomService.INSTANCE.setRooms(roomListPacket.getRooms());
        } else if(packet instanceof HelloWorldPacket) {
            // TODO alert user
            logger.info("Successfully connected to the server !");
        }
    }
}
