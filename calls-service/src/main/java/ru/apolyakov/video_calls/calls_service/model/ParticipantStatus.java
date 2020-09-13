package ru.apolyakov.video_calls.calls_service.model;

public enum ParticipantStatus {
    NEW, // call was never accepted
    REJECTED, // not to show in calls list forever
    ACCEPTED, // not to show in calls list while not canceled
    CANCELED  // call is canceled and can be accepted
}
