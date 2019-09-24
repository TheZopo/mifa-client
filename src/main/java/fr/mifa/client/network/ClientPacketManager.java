package fr.mifa.client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mifa.client.services.MessageService;
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
            MessageService.INSTANCE.displayText(joinedRoomPacket.getNickname()  + " joined the room !", joinedRoomPacket.getRoomId());
        } else if(packet instanceof LeftRoomPacket) {
            LeftRoomPacket leftRoomPacket = (LeftRoomPacket) packet;
            MessageService.INSTANCE.displayText(leftRoomPacket.getNickname()  + " left the room !", leftRoomPacket.getRoomId());
        } else if(packet instanceof MessageSentPacket) {
            MessageSentPacket messageSentPacket = (MessageSentPacket) packet;
            MessageService.INSTANCE.displayMessage(messageSentPacket.getMessage());
        } else if(packet instanceof RoomListPacket) {
            //TODO: listen from GUI
        } else if(packet instanceof HelloWorldPacket) {
            //TODO: something else ?
            logger.info("Successfully connected to the server !");
        }
    }
}
