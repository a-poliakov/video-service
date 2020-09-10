package ru.apolyakov.client_app.widget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.apolyakov.client_app.model.UserDto;
import ru.apolyakov.client_app.service.AuthService;
import ru.apolyakov.client_app.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListDataProvider implements SearchListDataProvider<UserDto> {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    public List<UserDto> findAll() {
        UserDto currentUser = authService.getCurrentUser();
        Collection<UserDto> allUsers = userService.findAll();
        if (CollectionUtils.isEmpty(allUsers)) {
            return Collections.emptyList();
        }
        return allUsers.stream()
                .filter(userDto -> !userDto.getLogin().equals(currentUser))
                .collect(Collectors.toList());
    }

    public List<UserDto> search(String prefix) {
        UserDto currentUser = authService.getCurrentUser();
        Collection<UserDto> allUsers = userService.findByPrefix(prefix);
        if (CollectionUtils.isEmpty(allUsers)) {
            return Collections.emptyList();
        }
        return allUsers.stream()
                .filter(userDto -> !userDto.getLogin().equals(currentUser))
                .collect(Collectors.toList());
    }
}
