package fr.mifa.client.gui.controls;

import com.jfoenix.controls.JFXTextArea;
import fr.mifa.client.services.RoomService;
import fr.mifa.client.services.UserService;
import fr.mifa.core.models.Message;
import fr.mifa.core.models.Room;
import fr.mifa.core.models.TextMessage;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChatroomControl extends GridPane {
    private static final Logger logger = LoggerFactory.getLogger(ChatroomControl.class);

    private Room room;

    @FXML
    private Label roomName;

    @FXML
    private VBox messagesList;

    @FXML
    private JFXTextArea message;

    @FXML
    private FontIcon sendBtn;

    @FXML
    private FontIcon sendFileBtn;

    @FXML
    private FontIcon leaveBtn;

    public ChatroomControl(Room room) {
        this.room = room;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(getClass().getResourceAsStream("/controls/ChatroomControl.fxml"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        sendBtn.setOnMouseClicked((EventHandler<? super MouseEvent>) e -> sendMessage());
        sendFileBtn.setOnMouseClicked((EventHandler<? super MouseEvent>) e -> sendFile());
        leaveBtn.setOnMouseClicked((EventHandler<? super MouseEvent>) e -> leaveRoom());
        this.setOnKeyReleased((EventHandler<? super KeyEvent>) this::handleInput);
    }

    @FXML
    public void initialize() {
        roomName.setText(room.getName());
        messagesList.getChildren().clear();
        String userNickname = UserService.INSTANCE.getUserNickname();
        for (Message message : room.getHistory()) {
            addMessage(message, message.getAuthorName().equals(userNickname) ? MessageType.ME : MessageType.OTHER);
        }
    }

    private void sendMessage() {
        if (message.validate()) {
            UserService.INSTANCE.sendText(message.getText());
            message.setText("");
        }
    }

    private void sendFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            UserService.INSTANCE.sendFile(selectedFile);
        }
    }

    private void leaveRoom() {
        RoomService.INSTANCE.leaveRoom(UserService.INSTANCE.getCurrentRoom());
    }

    //TODO: naming
    public void addMessage(Message message, MessageType messageType) {
        ObservableList<Node> childMessages = messagesList.getChildren();
        String author = message.getAuthorName();
        MessageControl lastMessage = childMessages.size() > 0 ? (MessageControl) messagesList.getChildren().get(childMessages.size() - 1) : null;

        if(lastMessage != null && lastMessage.getAuthorName().equals(author)) {
            lastMessage.addMessage(message);
        } else {
            ArrayList<Message> subMessagesList = new ArrayList<>();
            subMessagesList.add(message);

            messagesList.getChildren().add(new MessageControl(author, subMessagesList, messageType));
        }

    }

    private void handleInput(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }
}
