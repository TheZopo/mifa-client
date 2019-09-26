package fr.mifa.client.gui.controls;

import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }

    @FXML
    public void initialize() {
        roomName.setText(room.getName());
    }

    private void sendMessage() {
        if (message.validate()) {
            UserService.INSTANCE.sendText(message.getText());
            message.setText("");
        }
    }

    //TODO: naming
    public void addMessage(String author, Message message, MessageType messageType) {
        ObservableList<Node> childMessages = messagesList.getChildren();
        MessageControl lastMessage = childMessages.size() > 0 ? (MessageControl) messagesList.getChildren().get(childMessages.size() - 1) : null;

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)message;
            String messageStr = textMessage.getText();
            if(lastMessage != null && lastMessage.getAuthorName().equals(author)) {
                lastMessage.addMessage(messageStr);
            } else {
                ArrayList<String> subMessagesList = new ArrayList<>();
                subMessagesList.add(messageStr);

                messagesList.getChildren().add(new MessageControl(author, subMessagesList, messageType));
            }
        }
        else {
            logger.warn("Unhandled message type" + message.getClass().getName());
        }
    }
}
