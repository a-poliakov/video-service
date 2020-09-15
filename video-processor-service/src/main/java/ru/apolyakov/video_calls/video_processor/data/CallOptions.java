package ru.apolyakov.video_calls.video_processor.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CallOptions implements Serializable  {
    @JsonProperty("enable_face_recognition")
    private boolean enableFaceRecognition;

    @JsonProperty("video_filters")
    private VideoFilters videoFilters;

    @Data
    @Builder
    public static class VideoFilters implements Serializable {
        @JsonProperty("filters")
        private List<String> filters;
    }
}
