package ru.apolyakov.client_app.widget;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SelectedItems<T> extends FlowPane {
//    private final Pane pane = new FlowPane(10, 10);
    private final Map<String, SelectItemWidget> codeToWidgetMap = new HashMap<>();
    private final Map<String, T> selectedItems = new HashMap<>();

    public void addItem(String code, String title, T value) throws IOException {
        if (codeToWidgetMap.containsKey(code)) {
            return;
        }
        EventHandler<? super MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SelectItemWidget selectItemWidget = codeToWidgetMap.get(code);
                getChildren().removeAll(selectItemWidget);
                codeToWidgetMap.remove(code);
                selectedItems.remove(code);
            }
        };
        selectedItems.put(code, value);

        URL selectItemWidgetUrl = getClass().getClassLoader().getResource("fxml/select-item.fxml");
        // URL selectItemWidgetUrl = getClass().getResource("inner2.fxml");

        FXMLLoader innerLoader = new FXMLLoader(selectItemWidgetUrl);

        // get insertion point from outer fxml
        //innerLoader.setRoot(this);
        SelectItemWidget selectItemWidget = innerLoader.load();
        selectItemWidget.init(code, title, eventHandler);

        //SelectItemWidget selectItemWidget = new SelectItemWidget(code, title, eventHandler);
        getChildren().addAll(selectItemWidget);
        codeToWidgetMap.putIfAbsent(code, selectItemWidget);
    }

    public Set<String> getSelectedItemCodes() {
        return selectedItems.keySet();
    }

    public Collection<T> getSelectedItems() {
        return selectedItems.values();
    }
}
