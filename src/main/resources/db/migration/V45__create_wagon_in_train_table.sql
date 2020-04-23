create table wagon_in_train
(
    id serial not null
        constraint wagon_in_train_pkey
            primary key,
    owner_id integer,
    brake_type integer,
    position integer not null,
    total_load_weight integer not null,
    train_composition_id integer
        constraint fk_wagon_in_train_train_composition
            references train_composition,
    wagon_id integer
        constraint fk_wagon_in_train_wagon
            references wagon
);