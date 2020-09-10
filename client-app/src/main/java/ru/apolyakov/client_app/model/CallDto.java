package ru.apolyakov.client_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
public class CallDto {
    private final Long id;
    private final Set<UserDto> participants;
}
