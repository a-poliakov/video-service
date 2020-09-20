package ru.apolyakov.video_calls.video_processor.config;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ExecutorConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.apolyakov.video_calls.video_processor.Constants;
import ru.apolyakov.video_calls.video_processor.config.properties.ClusterNodeProperties;
import ru.apolyakov.video_calls.video_processor.config.properties.ThreadPoolProperties;

import java.util.Map;

@Slf4j
@Configuration
public class VideoProcessorServiceConfiguration {
    @Getter
    private IgniteNameFilter igniteNameFilter;
    @Getter
    private final ApplicationContext applicationContext;
    @Getter
    private final ClusterNodeProperties clusterNodeProperties;

    private Ignite ignite;

    public VideoProcessorServiceConfiguration(ApplicationContext applicationContext,
                                              ClusterNodeProperties clusterNodeProperties) {
        this.applicationContext = applicationContext;
        this.clusterNodeProperties = clusterNodeProperties;
    }

    @Bean
    IgniteLogger igniteLogger() {
        return new Slf4jLogger();
    }

    @Bean
    @Qualifier("userAttrs")
    Map<String, ?> userAttrs(ThreadPoolProperties threadPoolProperties, ClusterNodeProperties clusterNodeProperties) {
        ImmutableMap.Builder<String, Object> stringObjectBuilder = new ImmutableMap.Builder<>();
        stringObjectBuilder.put("cluster.service.id", clusterNodeProperties.getServiceId());
        stringObjectBuilder.put("cluster.service.type", clusterNodeProperties.getServiceType());
        stringObjectBuilder.put("ignite.thread-pool.service.size", threadPoolProperties.getServiceSize());
        stringObjectBuilder.put("ignite.thread-pool.public.size", threadPoolProperties.getPublicSize());
        stringObjectBuilder.build();
        return stringObjectBuilder.build();
    }

    @Bean
    Ignite ignite(VideoCallsIgniteConfiguration igniteConfiguration,
                  IgniteNameFilter igniteNameFilter,
                  CacheConfiguration[] cacheConfigurations) throws IgniteCheckedException {
        this.igniteNameFilter = igniteNameFilter;
        igniteConfiguration.setCacheConfiguration(cacheConfigurations);
        Ignite ignite = IgniteSpring.start(igniteConfiguration, applicationContext);
        ignite.events().enableLocal(EventType.EVTS_DISCOVERY);
        this.ignite = ignite;
        return ignite;
    }

    @Bean
    public ExecutorConfiguration dependentTaskExecutorConfiguration(@Value("${ignite.thread-pool.public.size:5}") int poolSize) {
        ExecutorConfiguration executorConfiguration = new ExecutorConfiguration(Constants.ExecConfiguration.DEPENDENT_TASK_EXECUTOR);
        executorConfiguration.setSize(poolSize);
        return executorConfiguration;
    }
}
