package ru.apolyakov.video_calls.video_processor;

public interface Constants {
    interface ExecConfiguration {
        String DEPENDENT_TASK_EXECUTOR = "dep";
    }

    interface Caches {
        String CALL_SID_TO_CALL_OPTIONS_CACHE = "call_sid_to_call_options_cache";
        String VIDEO_FRAMES_CACHE = "video_frames_cache";
    }

    interface Services {
        String VIDEO_BUFFER_CACHE_SERVICE = "video_buffer_cache_svc";
        String CALL_SID_TO_CALL_OPTIONS_CACHE_SERVICE = "call_sid_to_call_options_cache_svc";
        String PROCEED_CALL_SCHEDULER_SERVICE = "proceed_call_scheduler_svc";
    }
}
