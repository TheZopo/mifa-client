package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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
        //TODO: load messages
    }

    public void sendMessage(MouseEvent mouseEvent) {

    }

    public void addRoom(MouseEvent mouseEvent) {

    }

    public void changeRoom(MouseEvent mouseEvent) {

    }
}
