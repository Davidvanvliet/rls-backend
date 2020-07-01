create table error_message
(
    id                       integer not null
        constraint error_message_pkey
            primary key,
    error_code               integer not null,
    free_text_field          varchar(255),
    message_status           varchar(255),
    severity                 numeric(19, 2),
    type_of_error            numeric(19, 2),
    custom_message_status_id integer
        constraint errorMessage_customMessage_id
            references custom_message_status
);

