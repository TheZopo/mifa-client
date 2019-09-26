package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;

import fr.mifa.client.gui.controls.MessageControl;
import fr.mifa.client.gui.controls.MessageType;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML
    JFXTextField roomToAdd;

    @FXML
    VBox roomList;

    @FXML
    VBox messagesList;

    @FXML
    JFXTextArea message;

    @FXML
    public void initialize() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Hello");
        messages.add("Hello my dear");
        messages.add("Hello my dear how are you ?");

        messagesList.getChildren().add(new MessageControl("Bastien", messages, MessageType.OTHER));

        messages.clear();
        messages.add("Hi !");
        messages.add("Hi ! I'm fine");
        messages.add("Hi ! I'm fine thanks you !");

        messagesList.getChildren().add(new MessageControl("Me", messages, MessageType.ME));


        //TODO: load messages
    }

    public void sendMessage(MouseEvent mouseEvent) {

    }

    public void addRoom(MouseEvent mouseEvent) {

    }

    public void changeRoom(MouseEvent mouseEvent) {

    }
}
