create table uic_header
(
    id                 serial  not null
        constraint uic_header_pkey
            primary key,
    compressed         boolean not null,
    encrypted          boolean not null,
    message_identifier varchar(255),
    message_li_host    varchar(255),
    signed             boolean not null
);