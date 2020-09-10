package ru.apolyakov.client_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.apolyakov.client_app.model.CallDto;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CallService {
    private final UserService userService;

    public Set<CallDto> getActiveCalls() {
        return Collections.emptySet();
    }
}
