package ru.apolyakov.client_app.widget;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectItemWidget extends HBox {
    private String code;

    @FXML
    private Label title;

    @FXML
    private Circle closeItem;

    public SelectItemWidget(String code, String title, EventHandler<? super MouseEvent> eventHandler) {
        super();
        Label titleLabel = new Label(title);
        titleLabel.setMinWidth(25);
        titleLabel.setMaxWidth(100);
        BackgroundFill[] backgroundFills = new BackgroundFill[]{new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)};
        Background EMPTY = new Background(backgroundFills, null);
        titleLabel.setBackground(EMPTY);
        getChildren().add(titleLabel);
        Circle close = new Circle();
        close.setStroke(Color.web("#fa4771"));
        close.setStrokeType(StrokeType.INSIDE);
        close.setOnMouseClicked(eventHandler);

        close.setRadius(5.0);
        getChildren().add(close);
        this.code = code;
    }

    @FXML
    public void initialize() {
        //ObservableMap<Object, Object> properties = this.getProperties();
//        this.getManagedChildren()
//        .forEach(child -> {
//            String childId = child.getId();
//            if (childId.equals("title")) {
//                title = (Label) child;
//            } else if (childId.equals("close")) {
//                close = (Circle) child;
//            }
//        });
    }

    public void init(String code, String titleText, EventHandler<? super MouseEvent> eventHandler) {
        this.code = code;

        this.getManagedChildren().forEach(child -> {
            String childId = child.getId();
            if (childId.equals("title")) {
                title = (Label) child;
            } else if (childId.equals("closeItem")) {
                closeItem = (Circle) child;
            }
        });

        closeItem.setOnMouseClicked(eventHandler);
        title.setText(titleText);
    }
}
