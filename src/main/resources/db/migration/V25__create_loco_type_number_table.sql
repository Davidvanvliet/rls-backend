create table loco_type_number
(
    id            serial not null
        constraint loco_type_number_pkey
            primary key,
    owner_id      integer,
    control_digit varchar(255),
    country_code  varchar(255),
    serial_number varchar(255),
    series_number varchar(255),
    type_code1    varchar(255),
    type_code2    varchar(255)
);