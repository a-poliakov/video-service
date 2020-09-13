package ru.apolyakov.video_calls.api_gateway.rest_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;
import ru.apolyakov.video_calls.api_gateway.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto result = userService.register(userDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
