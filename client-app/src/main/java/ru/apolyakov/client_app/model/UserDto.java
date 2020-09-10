package ru.apolyakov.client_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto implements Titled, HasCode {
    private final String login;
    private final String password;

    private final String firstName;
    private final String secondName;

    @Override
    public String getCode() {
        return login;
    }

    @Override
    public String getTitle() {
        return String.format("%s (%s %s)", login, firstName, secondName);
    }
}
