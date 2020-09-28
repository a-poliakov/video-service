package ru.apolyakov.video_calls.video_processor.service.streamer;

import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.stream.StreamSingleTupleExtractor;
import org.apache.ignite.stream.kafka.KafkaStreamer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;
import ru.apolyakov.video_calls.video_processor.Constants;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class KafkaStreamerImpl {
    private final Ignite ignite;

    private KafkaStreamer<String, String> kafkaStreamer;
    private IgniteDataStreamer<String, String> stmr;

    private List<String> someKafkaTopic = new ArrayList<>();
    private Properties kafkaConsumerConfig = new Properties();

    StreamSingleTupleExtractor<ConsumerRecord, String, String> strExtractor = stringStringConsumerRecord -> null;

//    @PostConstruct
    public void init() {
        kafkaStreamer = new KafkaStreamer<>();

        stmr = ignite.dataStreamer(Constants.Caches.VIDEO_FRAMES_CACHE);

        // allow overwriting cache data
        stmr.allowOverwrite(true);

        kafkaStreamer.setIgnite(ignite);
        kafkaStreamer.setStreamer(stmr);

        // set the topic
        kafkaStreamer.setTopic(someKafkaTopic);

        // set the number of threads to process Kafka streams
        kafkaStreamer.setThreads(4);

        // set Kafka consumer configurations
        kafkaStreamer.setConsumerConfig(kafkaConsumerConfig);

        // set extractor
        kafkaStreamer.setSingleTupleExtractor(strExtractor);

        kafkaStreamer.start();
    }

//    @PreDestroy
    public void preDestroy() {
        // stop on shutdown
        kafkaStreamer.stop();

        stmr.close();
    }
}
