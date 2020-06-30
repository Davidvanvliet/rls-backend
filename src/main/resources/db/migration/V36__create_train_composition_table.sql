create table train_composition
(
    id                            serial  not null
        constraint train_composition_pkey
            primary key,
    owner_id                      integer,
    brake_type                    varchar(255),
    livestock_or_people_indicator integer not null,
    journey_section_id            integer
);