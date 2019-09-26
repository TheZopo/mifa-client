package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;

import fr.mifa.client.gui.controls.MessageControl;
import fr.mifa.client.gui.controls.MessageType;
import fr.mifa.client.services.NetworkService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ChatController {
    public static ChatController instance;

    @FXML
    JFXTextField roomToAdd;

    @FXML
    VBox roomList;

    @FXML
    VBox messagesList;

    @FXML
    JFXTextArea message;

    public ChatController() {
        instance = this;
    }

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

    @FXML
    public void sendMessage(MouseEvent mouseEvent) {
        //TODO: validate
        NetworkService.INSTANCE.sendTextMessage("room", message.getText());
    }

    @FXML
    public void addRoom(MouseEvent mouseEvent) {

    }

    @FXML
    public void changeRoom(MouseEvent mouseEvent) {

    }

    //TODO: naming
    public void addMessage(String author, String message, MessageType messageType) {
        MessageControl lastMessage = (MessageControl) messagesList.getChildren().get(messagesList.getChildren().size() - 1);

        if(lastMessage.getAuthorName().equals(author)) {
            lastMessage.addMessage(message);
        } else {
            ArrayList<String> subMessagesList = new ArrayList<>();
            subMessagesList.add(message);

            messagesList.getChildren().add(new MessageControl(author, subMessagesList, messageType));
        }
    }
}
