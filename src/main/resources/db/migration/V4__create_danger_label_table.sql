create table danger_label
(
    id          serial not null
        constraint danger_label_pkey
            primary key,
    code        varchar(255),
    description varchar(255)
);