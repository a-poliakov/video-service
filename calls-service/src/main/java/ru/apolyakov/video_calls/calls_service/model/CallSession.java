package ru.apolyakov.video_calls.calls_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "call_sessions")
public class CallSession {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="sid_seq")
    @SequenceGenerator(name="sid_seq",
            sequenceName="SEQ_SID", allocationSize=10)
    @Column(name="sid", updatable=false, nullable=false)
    private String sid;

    private String title;

    private CallStatus status;

    @Column(name = "owner_login")
    @NotNull
    private String ownerLogin;

    @Column(name = "options")
    @NotNull
    private String callOptionsJson;

    @OneToMany(mappedBy="session")
    private Set<CallParticipantDetails> participantDetails;
}
