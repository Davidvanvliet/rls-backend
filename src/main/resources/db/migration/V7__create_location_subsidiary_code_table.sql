create table location_subsidiary_code
(
    id          serial not null
        constraint location_subsidiary_code_pkey
            primary key,
    description varchar(255),
    extra       varchar(255),
    value       varchar(255)
);