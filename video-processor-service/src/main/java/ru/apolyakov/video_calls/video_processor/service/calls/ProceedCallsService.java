package ru.apolyakov.video_calls.video_processor.service.calls;

public interface ProceedCallsService {
    void proceedCall(String callSid);

    void proceedCalls();
}
