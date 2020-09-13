package ru.apolyakov.video_calls.api_gateway.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDtoToUserConverter implements Converter<UserDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserDto source) {
        String encrytedPassword = passwordEncoder.encode(source.getPassword());
        return User.builder()
                .login(source.getLogin())
                .firstName(source.getFirstName())
                .secondName(source.getSecondName())
                .password(encrytedPassword)
                .build();
    }
}
