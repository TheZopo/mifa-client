package fr.mifa.client;

import fr.mifa.client.gui.GUIManager;
import fr.mifa.client.network.ClientPacketManager;
import fr.mifa.core.network.PacketManager;
import fr.mifa.core.network.protocol.AuthPacket;
import fr.mifa.core.network.protocol.JoinRoomPacket;
import javafx.application.Application;
import javafx.stage.Stage;

public class MIFAClient extends Application {
    public static void main(String[] args) {
        System.out.println("Hello World mifa-client !");

        //TODO: arguments for host and port
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GUIManager guiManager = new GUIManager(stage);
        networkTest();
    }

    public void networkTest() {
        PacketManager client = new ClientPacketManager();
        client.connect("localhost", 2021);
        client.start();

        client.send(new AuthPacket("dummy"));
        client.send(new JoinRoomPacket(1));
    }
}
