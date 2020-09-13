package ru.apolyakov.video_calls.calls_service.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@IdClass(CallParticipantDetails.CallParticipantPK.class)
@Table(name = "call_participants")
public class CallParticipantDetails implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="sid", nullable=false)
    private CallSession session;

//    @Id
//    private String sessionId;
    @Id
    private String login;

    private ParticipantStatus status;

    @AllArgsConstructor
    @Data
    @EqualsAndHashCode
    public static class CallParticipantPK implements Serializable {
        private CallSession session;
        private String login;
    }
}
