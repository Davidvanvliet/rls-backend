create table traction_in_train
(
    id                   serial not null
        constraint traction_in_train_pkey
            primary key,
    driver_indication    integer,
    position             integer,
    traction_id          integer
        constraint fk_traction_in_train_traction
            references traction,
    traction_mode_id    integer
        constraint fk_traction_traction_mode
            references traction_mode,

    train_composition_id integer
        constraint fk_traction_in_train_train
            references train_composition
);