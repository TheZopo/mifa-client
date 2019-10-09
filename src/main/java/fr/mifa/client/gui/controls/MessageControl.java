package fr.mifa.client.gui.controls;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.mifa.core.models.FileMessage;
import fr.mifa.core.models.Message;
import fr.mifa.core.models.TextMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

public class MessageControl extends HBox {
    private final List<String> imageExtensions = Arrays.asList("png", "jpg", "jpeg", "gif");

    private String authorName;
    private ArrayList<Message> messagesList;
    private MessageType messageType;

    @FXML
    private Label author;

    @FXML
    private VBox messages;

    public MessageControl(String authorName, ArrayList<Message> messagesList, MessageType messageType) {
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

        StackPane stickyNotesPane = new StackPane();
        stickyNotesPane.setPrefSize(200, 200);
        stickyNotesPane.setStyle("-fx-background-color: yellow;");

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        this.hoverProperty().addListener((obs, oldVal, newValue) -> {
            //TODO
            if (newValue) {
                Bounds bnds = this.localToScreen(this.getLayoutBounds());
                double x = bnds.getMinX();//- (stickyNotesPane.getWidth() / 2) + (this.getWidth() / 2);
                double y = bnds.getMinY();//- stickyNotesPane.getHeight();
                popup.show(this, x, y);
            } else {
                popup.hide();
            }
        });
    }

    @FXML
    public void initialize() {
        author.setText(authorName);
        for(Message message : messagesList) {
            displayMessage(message);
        }
    }

    public void addMessage(Message message) {
        messagesList.add(message);
        displayMessage(message);
    }

    private void displayMessage(Message message) {
        if (message instanceof TextMessage) {
            messages.getChildren().add(new Label(((TextMessage)message).getText()));
        }
        else if (message instanceof FileMessage) {
            FileMessage fileMessage = (FileMessage)message;
            String extension = "";

            int i = fileMessage.getFilename().lastIndexOf('.');
            int p = Math.max(fileMessage.getFilename().lastIndexOf('/'), fileMessage.getFilename().lastIndexOf('\\'));

            if (i > p) {
                extension = fileMessage.getFilename().substring(i+1);
            }

            if (imageExtensions.contains(extension)) {
                Image img = new Image(new ByteArrayInputStream(fileMessage.getContent()));
                ImageView imageView = new ImageView(img);
                messages.getChildren().add(imageView);
            }

            Hyperlink hyperlink = new Hyperlink(fileMessage.getFilename());
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Saving your file");
                    fileChooser.setInitialFileName(fileMessage.getFilename());
                    File file = fileChooser.showSaveDialog(getScene().getWindow());
                    try (FileOutputStream out = new FileOutputStream(file))
                    {
                        out.write(fileMessage.getContent());
                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {

                    }
                }
            });
            messages.getChildren().add(hyperlink);

        }
    }

    public String getAuthorName() {
        return authorName;
    }
}
