create table location
(
    location_primary_code integer not null
        constraint location_pkey
            primary key,
    active_flag           integer not null,
    code                  varchar(255),
    country_code_iso      varchar(255),
    latitude              varchar(255),
    longitude             varchar(255),
    primary_location_name varchar(255),
    responsible_code      varchar(255)
        constraint fk_location_company
            references company (code)
);

alter table location
    owner to postgres;

