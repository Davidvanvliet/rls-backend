create table custom_message_status
(
    id                              integer not null
        constraint custom_message_status_pkey
            primary key,
    ack_knowledge_message_date_time timestamp,
    acknowledged                    boolean not null,
    message_identifier              varchar(255),
    sent_by                         varchar(255),
    sent_to_remote_li               varchar(255),
    error_message_id                integer,
    train_id                        integer
        constraint train_customMessage_id
            references train
);
