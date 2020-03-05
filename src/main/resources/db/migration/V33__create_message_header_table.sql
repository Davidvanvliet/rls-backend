create table message_header
(
    id                   serial  not null
        constraint message_header_pkey
            primary key,
    owner_id             integer not null,
    sender_reference     varchar(255),
    message_reference_id integer
        constraint fk_message_header_message_reference
            references message_reference,
    recipient_id         integer
        constraint fk_message_header_recipient
            references company,
    sender_id            integer
        constraint fk_message_header_sender
            references company
);