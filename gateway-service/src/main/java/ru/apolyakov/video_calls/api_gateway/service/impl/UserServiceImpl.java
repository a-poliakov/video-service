package ru.apolyakov.video_calls.api_gateway.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.apolyakov.video_calls.api_gateway.dto.UserDto;
import ru.apolyakov.video_calls.api_gateway.model.User;
import ru.apolyakov.video_calls.api_gateway.model.UserDtoToUserConverter;
import ru.apolyakov.video_calls.api_gateway.model.UserToUserDtoConverter;
import ru.apolyakov.video_calls.api_gateway.repository.UserRepository;
import ru.apolyakov.video_calls.api_gateway.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s).orElseThrow(() -> new UsernameNotFoundException("User " + s + " not found!"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new HashSet<>());
    }

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        User newUser = userDtoToUserConverter.convert(userDto);
        newUser = userRepository.save(newUser);
        //Optional<User> savedUser = userDao.findByLogin(userDto.getLogin());
        //savedUser.ifPresent(user -> userDto.setId(user.getId()));
        userDto.setId(newUser.getId());
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) {
        try {
            User user = userRepository.findByLogin(login).orElse(null);
            return user == null ? null : userToUserDtoConverter.convert(user);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchUsersByLike(String pattern) {
        List<Optional<User>> optionalList = userRepository.findAllByLoginLikeOrFirstNameLikeOrSecondNameIsLike(pattern, pattern, pattern);
        return optionalList.stream()
                .filter(Optional::isPresent)
                .map(optional -> userToUserDtoConverter.convert(optional.get()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDetails> findByToken(String token) {
        return Optional.empty();
    }
}
