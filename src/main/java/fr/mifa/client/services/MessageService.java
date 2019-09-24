package fr.mifa.client.services;

import fr.mifa.core.models.Message;
import fr.mifa.core.models.TextMessage;

public enum MessageService {
    INSTANCE;

    public void displayText(String text, int roomId) {
        System.out.println("[ROOM " + roomId + "]" + text);
    }

    public void displayMessage(Message message) {
        if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("[ROOM " + message.getRoomId() + "] [" + message.getAuthorName() + "] " + textMessage.getText());
        }
    }
}
