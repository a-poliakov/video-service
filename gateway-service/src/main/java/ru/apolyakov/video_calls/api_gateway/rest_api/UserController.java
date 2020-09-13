package ru.apolyakov.video_calls.api_gateway.rest_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;
import ru.apolyakov.video_calls.api_gateway.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam(required = true, name = "search_pattern") String pattern) {
        List<UserDto> result = userService.searchUsersByLike(pattern);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
