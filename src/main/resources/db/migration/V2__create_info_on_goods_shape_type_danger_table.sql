create table info_on_goods_shape_type_danger
(
    id          serial not null
        constraint info_on_goods_shape_type_danger_pkey
            primary key,
    description varchar(255),
    value       varchar(255)
);