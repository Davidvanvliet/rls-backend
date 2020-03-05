create table traction_type
(
    id          serial not null
        constraint traction_type_pkey
            primary key,
    code        varchar(255),
    description varchar(255)
);