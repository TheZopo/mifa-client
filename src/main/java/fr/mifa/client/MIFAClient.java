package fr.mifa.client;

import fr.mifa.core.models.TextMessage;
import fr.mifa.core.network.Client;
import fr.mifa.core.network.protocol.MessagePacket;

import java.util.Scanner;

public class MIFAClient {
    public static void main(String[] args) {
        System.out.println("Hello World mifa-client !");

        Client client = new Client();
        client.connect("localhost", 2021);

        try ( Scanner scanner = new Scanner( System.in ) ) {
            while( true ) {
                System.out.print( "> " );
                String text = scanner.nextLine();

                TextMessage message = new TextMessage();
                message.setText(text);
                message.setRoomId(1);

                MessagePacket packet = new MessagePacket();
                packet.setMessage(message);
                client.send(packet);
            }
        }
    }
}
