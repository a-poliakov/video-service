package ru.apolyakov.video_calls.api_gateway.rest_api.services_proxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserCallSessionReq {
    @JsonProperty("call_sid")
    private String sid;
    @JsonProperty("user_login")
    private String userLogin;
    @JsonProperty("state")
    private String participationState;
}
