package ru.apolyakov.video_calls.calls_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
public class CallDto implements Serializable {
    @JsonProperty("sid")
    private String sessionId;

    private String title;

    @JsonProperty("owner")
    private String ownerLogin;

    @JsonProperty("participants")
    private Set<String> participantLogins;

    @JsonProperty("options")
    private CallOptions options;
}
