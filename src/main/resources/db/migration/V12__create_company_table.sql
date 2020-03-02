create table company
(
    id                   serial  not null
        constraint company_pkey
            primary key,
    active_flag          integer not null,
    central_entity_flag  integer not null,
    code                 varchar(255)
        constraint uk_company_code
            unique,
    country_iso          varchar(255),
    freight_flag         integer not null,
    infrastructure_flag  integer not null,
    name                 varchar(255),
    national_entity_flag integer not null,
    other_company_flag   integer not null,
    passenger_flag       integer not null,
    short_name           varchar(255),
    url                  varchar(255)
);