package ru.apolyakov.video_calls.calls_service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.calls_service.dto.CallDto;
import ru.apolyakov.video_calls.calls_service.dto.CallOptions;

@Component
public class CallSessionToCallDtoConverter implements Converter<CallSession, CallDto> {
    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public CallDto convert(CallSession source) {
        return CallDto.builder()
                .sessionId(source.getSid())
                .title(source.getTitle())
                .options(mapper.readValue(source.getCallOptionsJson(), CallOptions.class))
                .ownerLogin(source.getOwnerLogin())
                //participants
                .build();
    }
}
