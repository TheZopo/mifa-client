package fr.mifa.client.services;

import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.core.network.PacketManager;
import fr.mifa.core.network.protocol.AuthPacket;
import fr.mifa.core.network.protocol.JoinRoomPacket;

public enum NetworkService {
    INSTANCE;

    public void connectToServer(String host, String nickname) {
        int port = 2021;

        if(host.contains(":")) {
            port = Integer.parseInt(host.split(":")[1]);
            host = host.split(":")[0];
        }

        PacketManager client = new ClientPacketManager();
        client.connect(host, 2021);
        client.start();

        client.send(new AuthPacket(nickname));
        client.send(new JoinRoomPacket(1));
    }
}
