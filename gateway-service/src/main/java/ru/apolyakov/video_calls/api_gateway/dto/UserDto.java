package ru.apolyakov.video_calls.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {
    @JsonProperty
    private long id;
    @JsonProperty
    @NotNull
    private String login;
    @JsonProperty
    private String password;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("second_name")
    private String secondName;
    @JsonProperty
    private String city;
}
