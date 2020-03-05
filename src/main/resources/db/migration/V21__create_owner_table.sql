create table owner
(
    id   serial not null
        constraint owner_pkey
            primary key,
    code varchar(255),
    name varchar(255)
);