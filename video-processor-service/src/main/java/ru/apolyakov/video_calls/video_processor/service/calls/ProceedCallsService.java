package ru.apolyakov.video_calls.video_processor.service.calls;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.video_processor.service.caches.call_options.CallOptionsCacheService;
import ru.apolyakov.video_calls.video_processor.service.caches.video_frames.VideoFramesCacheService;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static ru.apolyakov.video_calls.video_processor.Constants.Services.VIDEO_BUFFER_CACHE_SERVICE;
import static ru.apolyakov.video_calls.video_processor.Constants.Services.CALL_SID_TO_CALL_OPTIONS_CACHE_SERVICE;

@Slf4j
@Component
public class ProceedCallsService {
    private final ExecutorService executor;
    public final Ignite ignite;

    public ProceedCallsService(Ignite ignite) {
        this.ignite = ignite;
        this.executor = new ThreadPoolExecutor(0, 20, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000));
    }

    public void proceedCall(String callSid) {
        VideoFramesCacheService videoFramesCacheService = getVideoFramesCacheServiceProxy();

    }

    public void proceedCalls() {
        CallOptionsCacheService callOptionsCacheService = getCallOptionsServiceProxy();
        List<String> callOptions = callOptionsCacheService.getKeys();
        List<Callable<Void>> alls = callOptions
                .stream()
                .map(callSid -> ((Callable<Void>) () -> {
                    try {
                        proceedCall(callSid);
                    } catch (Exception ex) {
                        log.error("ERROR proceed call id={}", callSid, ex);
                    }
                    return null;
                }))
                .collect(Collectors.toList());
        try {
            List<Future<Void>> all = executor.invokeAll(alls);
            for (Future<Void> future : all) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private VideoFramesCacheService getVideoFramesCacheServiceProxy() {
        return ignite.services().service(VIDEO_BUFFER_CACHE_SERVICE);
    }

    private CallOptionsCacheService getCallOptionsServiceProxy() {
        return ignite.services().service(CALL_SID_TO_CALL_OPTIONS_CACHE_SERVICE);
    }
}
