CREATE SEQUENCE public.seq_sid
    INCREMENT 10
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE if not exists public.call_sessions
(
    sid character varying(255) NOT NULL,
    options character varying(255) NOT NULL,
    owner_login character varying(255) NOT NULL,
    status integer,
    title character varying(255),
    CONSTRAINT call_sessions_pkey PRIMARY KEY (sid)
);


CREATE TABLE if not exists call_participants
(
    login character varying(255) NOT NULL,
    status integer,
    sid character varying(255) NOT NULL,
    CONSTRAINT call_participants_pkey PRIMARY KEY (sid, login),
    CONSTRAINT fkf7awcunpndehjpexsg16kgb9s FOREIGN KEY (sid)
        REFERENCES call_sessions (sid) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
