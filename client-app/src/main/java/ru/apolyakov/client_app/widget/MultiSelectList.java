package ru.apolyakov.client_app.widget;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.apolyakov.client_app.model.HasCode;
import ru.apolyakov.client_app.model.Titled;

import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class MultiSelectList<T extends Titled & HasCode> extends VBox {
    private SelectedItems<T> selectedItems;
    private AutoCompleteComboBoxListener<T> autoCompleteComboBoxListener;
    private SearchListDataProvider<T> dataProvider;

    private ComboBox<T> selectItemsComboBox;

    public void init(SearchListDataProvider<T> dataProvider,
                           StringConverter<T> tStringConverter,
                           Callback<ListView<T>, ListCell<T>> listViewListCellCallback) {
        this.dataProvider = dataProvider;

        this.getManagedChildren().forEach(child -> {
            String childId = child.getId();
            if (childId.equals("selectItemsComboBox")) {
                selectItemsComboBox = (ComboBox) child;
            }
        });

//        ComboBox<T> comboBox = new ComboBox<>();
//        comboBox.setPrefWidth(310.0);
        selectItemsComboBox.setConverter(tStringConverter);
        selectItemsComboBox.setCellFactory(listViewListCellCallback);
        // Update the message Label when the selected item changes
        selectItemsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                if (newValue == null) {
                    return;
                }
                String code = newValue.getCode();
                String title = newValue.getTitle();
                try {
                    selectedItems.addItem(code, title, newValue);
                } catch (IOException e) {
                    log.error("Error while loading select_item widget: {}", e.getMessage());
                }
            }
        });
        selectedItems = new SelectedItems<>();
        autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(selectItemsComboBox, selectedItems, dataProvider);
        getChildren().addAll(selectedItems);
    }
}
