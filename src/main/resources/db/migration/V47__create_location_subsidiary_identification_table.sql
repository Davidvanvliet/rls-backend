create table location_subsidiary_identification
(
    id integer not null
        constraint location_subsidiary_identification_pkey
            primary key,
    extra varchar(255),
    location_subsidiary_code varchar(255),
    location_subsidiary_name varchar(255),
    allocation_company_id integer
        constraint fk_location_subsidiary_identification_company
            references company
);