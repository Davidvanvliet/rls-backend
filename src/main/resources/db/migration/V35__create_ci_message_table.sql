create table ci_message
(
    id              serial  not null
        constraint ci_message_pkey
            primary key,
    owner_id        integer,
    create_date     timestamp,
    post_date       timestamp,
    posted          boolean not null,
    uic_header_id   integer
        constraint fk_ci_message_uic_header
            references uic_header,
    uic_request_id  integer
        constraint fk_ci_message_uic_request
            references uic_request,
    uic_response_id integer
        constraint fk_ci_message_uic_response
            references uic_response
);
