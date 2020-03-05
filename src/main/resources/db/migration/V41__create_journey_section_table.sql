create table journey_section
(
    id                                                serial not null
        constraint journey_section_pkey
            primary key,
    owner_id                                          integer,
    journey_section_destination_location_primary_code integer
        constraint fk_journey_section_destination
            references location,
    journey_section_origin_location_primary_code      integer
        constraint fk_journey_section_origin
            references location,
    responsibility_actual_section_id                  integer
        constraint fk_journey_responsibility_actual
            references responsibility,
    responsibility_next_section_id                    integer
        constraint fk_journey_responsibility_next
            references responsibility,
    train_id                                          integer
        constraint fk_journey_train
            references train,
    train_composition_id                              integer
        constraint fk_journey_train_composition
            references train_composition
);