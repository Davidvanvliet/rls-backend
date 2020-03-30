create table traction
(
    id                  serial  not null
        constraint traction_pkey
            primary key,
    owner_id            integer,
    brake_weight        integer not null,
    length_over_buffers integer not null,
    loco_number         varchar(255),
    loco_type_number    varchar(255),
    number_of_axles     integer not null,
    type_name           varchar(255),
    weight              integer not null,
    traction_type_id    integer
        constraint fk_traction_traction_type
            references traction_type
);

alter table traction
    owner to postgres;

