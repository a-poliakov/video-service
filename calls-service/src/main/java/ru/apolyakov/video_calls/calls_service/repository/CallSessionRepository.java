package ru.apolyakov.video_calls.calls_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.apolyakov.video_calls.calls_service.model.CallSession;

public interface CallSessionRepository extends JpaRepository<CallSession, String> {
}
