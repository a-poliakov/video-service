package ru.apolyakov.client_app.widget;

import ru.apolyakov.client_app.model.UserDto;

import java.util.List;

public interface SearchListDataProvider<T> {
    List<T> findAll();

    List<T> search(String prefix);
}
