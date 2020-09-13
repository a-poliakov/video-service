package ru.apolyakov.video_calls.api_gateway.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {
    UserDto register(UserDto userDto);

    UserDto findUserByLogin(String login);

    List<UserDto> searchUsersByLike(String pattern);

    Optional<UserDetails> findByToken(String token);
}
