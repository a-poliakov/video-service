package ru.apolyakov.client_app.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.apolyakov.client_app.config.ControllersConfiguration;
import ru.apolyakov.client_app.service.AuthService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
public class LoginController extends AbstractWindowController{
    @Autowired
    private AuthService authService;

    @Autowired
    @Qualifier("authView")
    private ControllersConfiguration.ViewHolder authView;

    @Autowired
    @Qualifier("mainView")
    private ControllersConfiguration.ViewHolder mainView;

    @Autowired
    @Qualifier("createCallView")
    private ControllersConfiguration.ViewHolder createCallView;

    @FXML
    ImageView ic;
    @FXML
    ImageView icon;
    @FXML
    Circle pic;

    ActionEvent event;

    @FXML
    Label login;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
        Image image = new Image(getClass().getClassLoader().getResource("fxml/pic.jpg").toExternalForm());
        pic.setFill(new ImagePattern(image));

        login.setText("Login");

        Image icoImage = new Image(getClass().getClassLoader().getResource("fxml/icon.png").toExternalForm());
        ic.setImage(icoImage);
        icon.setImage(icoImage);

        Parent root = authView.getParent();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setX(event.getScreenX() - xOffset);
            appStage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    public void back(MouseEvent event) throws IOException {
        try {
            authService.login("test", "123");
        } catch (Exception e) {
            log.error("Error while SignIn: {}", e.getMessage());
            return;
        }
        Parent mainViewParent = createCallView.getParent();

        //Parent mainViewParent = FXMLLoader.load(getClass().getResource("fxml/pin.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainViewParent.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        mainViewParent.setOnMouseDragged(event12 -> {
            appStage.setX(event12.getScreenX() - xOffset);
            appStage.setY(event12.getScreenY() - yOffset);
        });

        Scene scene = new Scene(mainViewParent);
        appStage.setScene(scene);
        appStage.show();

    }
}
