package ru.apolyakov.video_calls.video_processor.service;

import com.google.common.collect.Sets;

import java.util.Set;

public class CallsServiceProxy {
    public Set<String> getActiveCallSessions() {
        return Sets.newHashSet();
    }
}
