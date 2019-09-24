package fr.mifa.client;

import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.core.models.TextMessage;
import fr.mifa.core.network.PacketManager;
import fr.mifa.core.network.protocol.AuthPacket;
import fr.mifa.core.network.protocol.JoinRoomPacket;
import fr.mifa.core.network.protocol.MessagePacket;

import java.util.Scanner;

public class MIFAClient {
    public static void main(String[] args) {
        System.out.println("Hello World mifa-client !");

        //TODO: arguments for host and port
        PacketManager client = new ClientPacketManager();
        client.connect("localhost", 2021);
        client.start();

        try ( Scanner scanner = new Scanner( System.in ) ) {
            System.out.println("Your nickname :");
            String nickname = scanner.nextLine();
            client.send(new AuthPacket(nickname));
            client.send(new JoinRoomPacket(1));

            while( true ) {
                System.out.print( "> " );
                String text = scanner.nextLine();

                TextMessage message = new TextMessage(1, text);
                client.send(new MessagePacket(message));
            }
        }
    }
}
