create table danger_goods_type
(
    id serial not null
        constraint danger_goods_type_pkey
            primary key,
    hazard_identification_number varchar(255),
    limited_quantity_indicator boolean not null,
    packing_group varchar(255),
    rid_class varchar(255),
    un_number varchar(255)
);