create table train_composition_message
(
    id                   serial  not null
        constraint train_composition_message_pkey
            primary key,
    owner_id             integer,
    message_date_time    timestamp,
    message_identifier   varchar(255),
    message_status       integer,
    message_type         integer not null,
    message_type_version varchar(255),
    sender_reference     varchar(255),
    recipient_id         integer
        constraint fk_train_composition_message_recipient
            references company,
    sender_id            integer
        constraint fk_train_composition_message_sender
            references company,
    train_id             integer
        constraint fk_train_composition_message_train
            references train
);

