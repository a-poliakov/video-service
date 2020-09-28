package ru.apolyakov.video_calls.api_gateway;

public interface Constants {
    String API_ENDPOINT = "/api";

    interface ServicesNames {
        String API_GATEWAY = "gateway-service";
        String CALLS_SERVICE = "calls-service";
        String VIDEO_PROCESSOR_SERVICE = "video-processor-service";
    }
}
