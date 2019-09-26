package fr.mifa.client.gui.controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageControl extends HBox {
    private String authorName;
    private ArrayList<String> messagesList;
    private MessageType messageType;

    @FXML
    private Label author;

    @FXML
    private VBox messages;

    public MessageControl(String authorName, ArrayList<String> messagesList, MessageType messageType) {
        this.authorName = authorName;
        this.messagesList = messagesList;
        this.messageType = messageType;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(getClass().getResourceAsStream("/controls/MessageControl.fxml"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if(this.messageType == MessageType.ME) {
            this.getStyleClass().add("message-me");
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(this.getChildren());
            Collections.swap(workingCollection, 0, 1);
            this.getChildren().setAll(workingCollection);
        } else {
            this.getStyleClass().add("message-other");
        }
    }

    @FXML
    public void initialize() {
        System.out.println(this.messages.getStyleClass());
        author.setText(authorName);
        for(String message : messagesList) {
            messages.getChildren().add(new Label(message));
        }
    }

    public void addMessage(String message) {
        messagesList.add(message);
        messages.getChildren().add(new Label(message));
    }
}
