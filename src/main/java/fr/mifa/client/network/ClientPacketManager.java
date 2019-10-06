package fr.mifa.client.network;

import fr.mifa.client.services.RoomService;
import fr.mifa.core.network.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mifa.core.network.PacketManager;

public class ClientPacketManager extends PacketManager {
    private static final Logger logger = LoggerFactory.getLogger(ClientPacketManager.class);

    @Override
    protected void processPacket(Packet packet) {
        if(packet instanceof JoinedRoomPacket) {
            JoinedRoomPacket joinedRoomPacket = (JoinedRoomPacket) packet;
            logger.debug("Received JoinedRoomPacket");
            RoomService.INSTANCE.userJoined(joinedRoomPacket.getRoom(), joinedRoomPacket.getNickname());
        } else if(packet instanceof LeftRoomPacket) {
            LeftRoomPacket leftRoomPacket = (LeftRoomPacket) packet;
            logger.debug("Received LeftRoomPacket");
            RoomService.INSTANCE.userLeft(leftRoomPacket.getRoomId(), leftRoomPacket.getNickname());
        } else if(packet instanceof RoomListPacket) {
            RoomListPacket roomListPacket = (RoomListPacket)packet;
            logger.debug("Received RoomListPacket");
            RoomService.INSTANCE.setRooms(roomListPacket.getRooms());
        } else if(packet instanceof HelloWorldPacket) {
            // TODO alert user
            logger.debug("Received HelloWorldPacket");
            logger.info("Successfully connected to the server !");
        }
        else if(packet instanceof DisconnectPacket) {
            logger.warn("Received DisconnectPacket");
            // TODO alert user
            System.exit(0);
        }
        else {
            logger.warn("Received unhandled packet : " + packet.getClass().getName());
        }
    }
}