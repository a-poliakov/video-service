package ru.apolyakov.client_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.apolyakov.client_app.config.ControllersConfiguration;
import ru.apolyakov.client_app.model.CallDto;
import ru.apolyakov.client_app.model.Contact;
import ru.apolyakov.client_app.service.CallService;
import ru.apolyakov.client_app.service.ContactService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

public class MainController  extends AbstractWindowController{
    // Инъекции JavaFX
    @FXML
    private TableView<CallDto> table;

    // Переменные
    private ObservableList<CallDto> data;

    private double xOffset = 0;
    private double yOffset = 0;

    @Autowired
    private CallService callService;

    @Autowired
    @Qualifier("createCallView")
    private ControllersConfiguration.ViewHolder createCallView;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     *
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     *
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct.
     * Который вызовется спрингом, после того,
     * как им будут произведены все оставшиеся инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @PostConstruct
    public void init() {
        Set<CallDto> activeCalls = callService.getActiveCalls();
        data = FXCollections.observableArrayList(activeCalls);

        // Добавляем столбцы к таблице
        TableColumn<CallDto, String> callTitleColumn = new TableColumn<>("Call title");
        callTitleColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CallDto, String> participantsColumn = new TableColumn<>("Participants");
        participantsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CallDto, String> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        table.getColumns().setAll(callTitleColumn, participantsColumn, actionsColumn);

        // Добавляем данные в таблицу
        table.setItems(data);
    }

    @FXML
    public void startCall(ActionEvent actionEvent) {
        Parent mainViewParent = createCallView.getParent();

        //Parent mainViewParent = FXMLLoader.load(getClass().getResource("fxml/pin.fxml"));
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    @FXML
    public void openSettings(ActionEvent actionEvent) {
        //todo
    }
}
