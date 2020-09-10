package ru.apolyakov.client_app.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.apolyakov.client_app.model.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class UserService {
    private RestTemplate restTemplate = new RestTemplate();

    public Collection<UserDto> findAll() {
        UserDto userDto1 = new UserDto("admin1", "123", "Админ", "Админыч");
        UserDto userDto2 = new UserDto("admin2", "123", "Админ", "Админыч");
        ArrayList<UserDto> result = new ArrayList<>();
        result.add(userDto1);
        result.add(userDto2);
        return result;
    }

    public Collection<UserDto> findByPrefix(String prefix) {
        UserDto userDto1 = new UserDto("admin1", "123", "Админ", "Админыч");
        UserDto userDto2 = new UserDto("admin2", "123", "Админ", "Админыч");
        ArrayList<UserDto> result = new ArrayList<>();
        result.add(userDto1);
        result.add(userDto2);
        return result;
    }
}
