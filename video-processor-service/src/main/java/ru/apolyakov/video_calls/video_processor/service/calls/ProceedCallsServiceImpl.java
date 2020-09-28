package ru.apolyakov.video_calls.video_processor.service.calls;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.lang.IgniteFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.video_processor.Constants;
import ru.apolyakov.video_calls.video_processor.config.IgniteNameFilter;
import ru.apolyakov.video_calls.video_processor.service.caches.call_options.CallOptionsCacheService;
import ru.apolyakov.video_calls.video_processor.service.caches.video_frames.VideoFramesCacheService;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static ru.apolyakov.video_calls.video_processor.Constants.Services.CALL_SID_TO_CALL_OPTIONS_CACHE_SERVICE;
import static ru.apolyakov.video_calls.video_processor.Constants.Services.VIDEO_BUFFER_CACHE_SERVICE;

@Slf4j
@Component("proceedCallsServiceImpl")
@DependsOn("ignite")
public class ProceedCallsServiceImpl implements ProceedCallsService {
    private static final Set<String> cacheNames = Collections.singleton(Constants.Caches.VIDEO_FRAMES_CACHE);

    private final ExecutorService executor;
    @Autowired
    private Ignite ignite;
    @Autowired
    private IgniteNameFilter filter;

    public ProceedCallsServiceImpl() {
        this.executor = new ThreadPoolExecutor(0, 20, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000));
    }

    public void proceedCall(String callSid) {
        VideoFramesCacheService videoFramesCacheService = getVideoFramesCacheServiceProxy();
        List<Pair<String, Long>> keysForCallSession = videoFramesCacheService.getKeysForCallSession(callSid);
        Map<Integer, List<Pair<String, Long>>> partIdToKeysMap = videoFramesCacheService.partIdToKeysMap(keysForCallSession);

        IgniteCompute igniteCompute = ignite.compute(ignite.cluster().forPredicate(filter))
                .withExecutor(Constants.ExecConfiguration.DEPENDENT_TASK_EXECUTOR);

        List<IgniteFuture<Void>> proceedFramesFutures = partIdToKeysMap.entrySet().stream()
                .map(integerListEntry -> {
                    int partId = integerListEntry.getKey();
                    Collection<Pair<String, Long>> batchData = integerListEntry.getValue();
                    ProceedUserCallSessionTask cacheBatchProceedCallable = new ProceedUserCallSessionTask(batchData);
                    return igniteCompute.affinityCallAsync(cacheNames, partId, cacheBatchProceedCallable);
                })
                .collect(Collectors.toList());

        proceedFramesFutures.parallelStream()
                .forEach(IgniteFuture::get);
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
