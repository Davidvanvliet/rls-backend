create table message_reference_ci
(
    id                   serial  not null
        constraint message_reference_ci_pkey
            primary key,
    message_date_time    timestamp,
    message_identifier   varchar(255),
    message_type         integer not null,
    message_type_version varchar(255)
);
