create table traincc_system
(
    id          serial not null
        constraint traincc_system_pkey
            primary key,
    description varchar(255),
    value       varchar(255)
);