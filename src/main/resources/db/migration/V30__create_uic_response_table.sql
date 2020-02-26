create table uic_response
(
    id                          serial not null
        constraint uic_response_pkey
            primary key,
    remoteliinstance_number     varchar(255),
    ack_indentifier             varchar(255),
    message_transport_mechanism varchar(255),
    recipient                   varchar(255),
    response_status             varchar(255),
    sender                      varchar(255),
    message_reference_id        integer
        constraint fk_uic_response_message_reference
            references message_reference
);