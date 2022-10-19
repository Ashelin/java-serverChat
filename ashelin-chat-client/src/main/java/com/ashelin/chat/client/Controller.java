package com.ashelin.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller  implements Initializable {
    private Network network;

    @FXML
    TextField msgField;
    @FXML
    TextArea mainArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new Network();
    }


    public void sendMsgAction(ActionEvent actionEvent) {
        network.sendMessage(msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
}
