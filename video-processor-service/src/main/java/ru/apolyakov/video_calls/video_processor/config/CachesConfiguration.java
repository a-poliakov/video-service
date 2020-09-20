package ru.apolyakov.video_calls.video_processor.config;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.apolyakov.video_calls.video_processor.config.properties.ClusterNodeProperties;
import ru.apolyakov.video_calls.video_processor.data.CallOptions;
import ru.apolyakov.video_calls.video_processor.data.VideoFrame;

import java.util.Objects;
import java.util.PriorityQueue;

import static ru.apolyakov.video_calls.video_processor.Constants.Caches.CALL_SID_TO_CALL_OPTIONS_CACHE;
import static ru.apolyakov.video_calls.video_processor.Constants.Caches.VIDEO_FRAMES_CACHE;

@Configuration
public class CachesConfiguration {
    @Bean
    public CacheConfiguration<String, CallOptions> callSidToCallOptionsCacheConfiguration(ClusterNodeProperties clusterNodeProperties,
                                                                                          @Value("${ignite.call_sid_to_call_options.cache.mode}") String groupCacheMode,
                                                                                          @Value("${ignite.call_sid_to_call_options.atomicity.mode}") String groupCacheAtomicityMode,
                                                                                          @Qualifier("callSidToCallOptionsBackupFactor") int backupFactor) {

        CacheConfiguration<String, CallOptions> cacheConfiguration = new CacheConfiguration<>(CALL_SID_TO_CALL_OPTIONS_CACHE);

        cacheConfiguration.setNodeFilter(new IgniteNameFilter(clusterNodeProperties.getServiceType()));
        cacheConfiguration.setCacheMode(CacheMode.valueOf(groupCacheMode));
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.valueOf(groupCacheAtomicityMode));
        cacheConfiguration.setAffinity(new RendezvousAffinityFunction(true));
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.IGNORE);

        if (!Objects.equals(CacheMode.valueOf(groupCacheMode), CacheMode.REPLICATED)) {
            cacheConfiguration.setBackups(backupFactor);
        }
        return cacheConfiguration;
    }

    @Bean
    public CacheConfiguration<Pair<String, Long>, PriorityQueue<VideoFrame>> videoFramesCacheConfiguration(ClusterNodeProperties clusterNodeProperties,
                                                                                                                    @Value("${ignite.call_sid_to_call_options.cache.mode}") String groupCacheMode,
                                                                                                                    @Value("${ignite.call_sid_to_call_options.atomicity.mode}") String groupCacheAtomicityMode,
                                                                                                                    @Qualifier("callSidToCallOptionsBackupFactor") int backupFactor) {

        CacheConfiguration<Pair<String, Long>, PriorityQueue<VideoFrame>> cacheConfiguration = new CacheConfiguration<>(VIDEO_FRAMES_CACHE);

        cacheConfiguration.setNodeFilter(new IgniteNameFilter(clusterNodeProperties.getServiceType()));
        cacheConfiguration.setCacheMode(CacheMode.valueOf(groupCacheMode));
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.valueOf(groupCacheAtomicityMode));
        cacheConfiguration.setAffinity(new RendezvousAffinityFunction(true));
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.IGNORE);

        if (!Objects.equals(CacheMode.valueOf(groupCacheMode), CacheMode.REPLICATED)) {
            cacheConfiguration.setBackups(backupFactor);
        }
        return cacheConfiguration;
    }

    @Bean("callSidToCallOptionsBackupFactor")
    public int groupBackupFactor(@Value("${ignite.call_sid_to_call_options.backup.factor:0}") int backupFactor) {
        return backupFactor;
    }
}
