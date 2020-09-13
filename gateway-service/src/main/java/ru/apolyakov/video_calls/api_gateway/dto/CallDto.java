package ru.apolyakov.video_calls.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Set;

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
