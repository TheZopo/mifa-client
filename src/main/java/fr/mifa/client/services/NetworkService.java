package fr.mifa.client.services;

import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.client.utils.ClientProperties;
import fr.mifa.core.network.PacketManager;
import fr.mifa.core.network.protocol.AuthPacket;
import fr.mifa.core.network.protocol.JoinRoomPacket;
import fr.mifa.core.network.protocol.MessagePacket;

public enum NetworkService {
    INSTANCE;

    private ClientPacketManager client;

    public void connectToServer(String host, String nickname) {
        if (host == null || "".equals(host)) {
            host = ClientProperties.INSTANCE.get("host", "localhost");
        }
        int port = Integer.parseInt(ClientProperties.INSTANCE.get("port", "2021"));
        if(host.contains(":")) {
            host = host.split(":")[0];
            port = Integer.parseInt(host.split(":")[1]);
        }

        client = new ClientPacketManager();
        client.connect(host, port);
        client.start();
        client.send(new AuthPacket(nickname));
    }
}
