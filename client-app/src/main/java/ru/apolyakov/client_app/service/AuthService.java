package ru.apolyakov.client_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.apolyakov.client_app.model.UserDto;

@Component
@Slf4j
public class AuthService {
    private RestTemplate restTemplate = new RestTemplate();

    public boolean login(String login, String password) throws Exception {
        log.info("Try to SignIn for user {}", login);
        return true;
    }

    public UserDto getCurrentUser() {
        return new UserDto("admin", "123", "Админ", "Админыч");
    }
}
