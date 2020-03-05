create table exceptional_gauging_code
(
    id          serial not null
        constraint exceptional_gauging_code_pkey
            primary key,
    description varchar(255),
    value       varchar(255)
);