package ru.apolyakov.client_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VideoCaptureService videoCaptureService;
    @Autowired
    private UserListDataProvider userListDataProvider;


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

    }
}
