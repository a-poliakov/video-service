package ru.apolyakov.video_calls.api_gateway.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .login(source.getLogin())
                .firstName(source.getFirstName())
                .secondName(source.getSecondName())
                .build();
    }
}
