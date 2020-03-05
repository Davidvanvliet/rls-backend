create table danger_goods_in_wagon
(
    id serial not null
        constraint danger_goods_in_wagon_pkey
            primary key,
    dangerous_goods_volume real,
    dangerous_goods_weight integer not null,
    danger_goods_type_id integer
        constraint fk_danger_goods_in_wagon_type
            references danger_goods_type,
    wagon_in_train_id integer
        constraint fk_danger_goods_in_wagon_train
            references wagon_in_train
);