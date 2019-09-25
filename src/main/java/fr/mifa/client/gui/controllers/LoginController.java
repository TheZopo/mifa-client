package fr.mifa.client.gui.controllers;

import com.jfoenix.controls.JFXTextField;

import fr.mifa.client.MIFAClient;
import fr.mifa.client.services.NetworkService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    JFXTextField host;

    @FXML
    JFXTextField nickname;

    public void login(ActionEvent actionEvent) {
        if(host.validate() && nickname.validate()) {
            NetworkService.INSTANCE.connectToServer(host.getText(), nickname.getText());
            MIFAClient.guiManager.changeScene("chat");
        }
    }
}
