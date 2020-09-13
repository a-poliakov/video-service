package ru.apolyakov.video_calls.calls_service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.calls_service.dto.CallDto;
import ru.apolyakov.video_calls.calls_service.dto.CallOptions;

@Component
@Slf4j
@RequiredArgsConstructor
public class CallDtoToCallSessionConverter implements Converter<CallDto, CallSession> {
    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public CallSession convert(CallDto source) {
        return CallSession.builder()
                .sid(source.getSessionId())
                .title(source.getTitle())
                .callOptionsJson(mapper.writeValueAsString(source.getOptions()))
                .ownerLogin(source.getOwnerLogin())
                //.status(source.getStatus())
                .build();
    }
}
