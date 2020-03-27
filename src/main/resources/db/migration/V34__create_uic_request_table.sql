create table uic_request
(
    id           serial not null
        constraint uic_request_pkey
            primary key,
    owner_id     integer,
    encoding     varchar(255),
    sender_alias varchar(255),
    signature    varchar(255),
    message      varchar(64000)
);