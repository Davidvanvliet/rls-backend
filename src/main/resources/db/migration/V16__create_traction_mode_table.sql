create table traction_mode
(
    id          serial not null
        constraint traction_mode_pkey
            primary key,
    code        varchar(255),
    description varchar(255)
);