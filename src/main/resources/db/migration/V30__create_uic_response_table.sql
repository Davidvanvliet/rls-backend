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
    message_date_time    		timestamp,
    message_identifier   		varchar(255),
    message_type         		integer not null,
    message_type_version 		varchar(255)
);