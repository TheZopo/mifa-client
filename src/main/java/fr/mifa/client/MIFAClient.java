package fr.mifa.client;

import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.core.network.PacketManager;

public class MIFAClient {
    public static void main(String[] args) {
        System.out.println("Hello World mifa-client !");

        //TODO: arguments for host and port
        PacketManager client = new ClientPacketManager();
        client.connect("localhost", 2021);
        client.start();
    }
}
