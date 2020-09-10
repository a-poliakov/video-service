package ru.apolyakov.client_app.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractWindowController {
    @FXML
    protected Circle min;
    @FXML
    protected Circle close;

    /**** minimize ****/
    @FXML
    public void minclick(MouseEvent event) throws IOException {
        ((Stage)((Circle)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    /**** close screen ****/
    @FXML
    public void closeclick(MouseEvent event) throws IOException {
        System.exit(0);
    }
}
