create table xml_message
(
    id       serial not null
        constraint xml_message_pkey
            primary key,
    owner_id integer,
    message  text
);