package ru.apolyakov.client_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.apolyakov.client_app.config.ControllersConfiguration;
import ru.apolyakov.client_app.model.UserDto;
import ru.apolyakov.client_app.service.VideoCaptureService;
import ru.apolyakov.client_app.widget.MultiSelectList;
import ru.apolyakov.client_app.widget.SelectItemWidget;
import ru.apolyakov.client_app.widget.UserListDataProvider;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

public class CreateCallController extends AbstractWindowController {
    @FXML
    public Label login;

    @FXML
    public AnchorPane selectUsersToCallPane;

    private MultiSelectList<UserDto> userDtoMultiSelectList;

    @Autowired
    private UserListDataProvider userListDataProvider;

    @Autowired
    @Qualifier("mainView")
    private ControllersConfiguration.ViewHolder mainView;

    @Autowired
    @Qualifier("callView")
    private ControllersConfiguration.ViewHolder callView;

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() throws IOException {
        StringConverter<UserDto> tStringConverter = new StringConverter<UserDto>() {
            @Override
            public String toString(UserDto object) {
                return object == null ? null : String.format("%s (%s %s)", object.getLogin(), object.getFirstName(), object.getSecondName());
            }

            @Override
            public UserDto fromString(String string) {
                String[] strings = string.split(" ");
                return UserDto.builder()
                        .login(strings[0])
                        .firstName(strings[1].startsWith("(") ? strings[1].substring(1) : strings[1])
                        .secondName(strings[2].endsWith(")") ? strings[2].substring(0, strings[2].indexOf(")")) : strings[2])
                        .build();
            }
        };
        Callback<ListView<UserDto>, ListCell<UserDto>> listViewListCellCallback = new Callback<ListView<UserDto>, ListCell<UserDto>>() {
            @Override
            public ListCell<UserDto> call(ListView<UserDto> param) {
                return new ListCell<UserDto>(){
                    @Override
                    protected void updateItem(UserDto item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            String text = String.format("%s (%s %s)", item.getLogin(), item.getFirstName(), item.getSecondName());
                            setText(text);
                        }
                    }
                } ;
            }
        };

        URL selectItemWidgetUrl = getClass().getClassLoader().getResource("fxml/multi-select-list.fxml");
        // URL selectItemWidgetUrl = getClass().getResource("inner2.fxml");

        FXMLLoader innerLoader = new FXMLLoader(selectItemWidgetUrl);

        // get insertion point from outer fxml
        //innerLoader.setRoot(this);
        userDtoMultiSelectList = innerLoader.load();
        userDtoMultiSelectList.init(userListDataProvider, tStringConverter, listViewListCellCallback);
        selectUsersToCallPane.getChildren().addAll(userDtoMultiSelectList);
    }

    public void call(MouseEvent mouseEvent) {
        Parent mainViewParent = callView.getParent();

        CallSessionController callViewController = (CallSessionController) callView.getController();
        callViewController.startCameraCapture();

        //Parent mainViewParent = FXMLLoader.load(getClass().getResource("fxml/pin.fxml"));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        mainViewParent.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        mainViewParent.setOnMouseDragged(event12 -> {
            appStage.setX(event12.getScreenX() - xOffset);
            appStage.setY(event12.getScreenY() - yOffset);
        });

        Scene scene;
        if (mainViewParent.getScene() == null) {
            scene = new Scene(mainViewParent);
        } else {
            scene = mainViewParent.getScene();
        }
        appStage.setScene(scene);
        appStage.show();
    }

    public void back(MouseEvent mouseEvent) {
        Parent mainViewParent = mainView.getParent();

        //Parent mainViewParent = FXMLLoader.load(getClass().getResource("fxml/pin.fxml"));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        mainViewParent.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        mainViewParent.setOnMouseDragged(event12 -> {
            appStage.setX(event12.getScreenX() - xOffset);
            appStage.setY(event12.getScreenY() - yOffset);
        });

        Scene scene;
        if (mainViewParent.getScene() == null) {
            scene = new Scene(mainViewParent);
        } else {
            scene = mainViewParent.getScene();
        }
        appStage.setScene(scene);
        appStage.show();
    }
}
