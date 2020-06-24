create table rolling_stock
(
    stock_type           varchar(31) not null,
    id                   serial      not null
        constraint rolling_stock_pkey
            primary key,
    position             integer,
    driver_indication    integer,
    brake_type           integer,
    total_load_weight    integer,
    train_composition_id integer
        constraint fk_rolling_stock_train_composition
            references train_composition,
    traction_id          integer
        constraint fk_rolling_stock_traction
            references traction,
    wagon_id             integer
        constraint fk_rolling_stock_wagon
            references wagon
);

