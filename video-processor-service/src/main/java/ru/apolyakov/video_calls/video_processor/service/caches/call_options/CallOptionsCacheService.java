package ru.apolyakov.video_calls.video_processor.service.caches.call_options;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import ru.apolyakov.video_calls.video_processor.Constants;
import ru.apolyakov.video_calls.video_processor.data.CallOptions;
import ru.apolyakov.video_calls.video_processor.service.caches.AbstractCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class CallOptionsCacheService extends AbstractCacheService<String, CallOptions> implements Service {
    @Override
    public void cancel(ServiceContext serviceContext) {

    }

    @Override
    public void init(ServiceContext serviceContext) throws Exception {
        getOrCreateCache();
    }

    @Override
    public void execute(ServiceContext serviceContext) throws Exception {

    }

    @Override
    protected String getCacheName() {
        return Constants.Caches.CALL_SID_TO_CALL_OPTIONS_CACHE;
    }

    public List<String> removeUnactiveSessions(Set<String> activeSids) {
        List<String> keys = new ArrayList<>();
        cache.query(new ScanQuery<>(null)).forEach(entry -> {
            if (activeSids.contains((String) entry.getKey())) {
                keys.add((String) entry.getKey());
            }
        });
        keys.forEach(this::delete);
        return keys;
    }

    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        cache.query(new ScanQuery<>(null)).forEach(entry -> keys.add((String) entry.getKey()));
        return keys;
    }

    @Override
    public String put(String key, CallOptions value) {
        cache.put(key, value);
        return key;
    }

    @Override
    public void delete(String key) {
        cache.remove(key);
    }
}
