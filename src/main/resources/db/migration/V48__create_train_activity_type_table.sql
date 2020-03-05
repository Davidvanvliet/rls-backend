create table train_activity_type
(
    id serial not null
        constraint train_activity_type_pkey
            primary key,
    code varchar(255)
        constraint idx_code
            unique,
    description varchar(255),
    journey_section_id integer
        constraint fk_train_activity_type_journey_section
            references journey_section
);