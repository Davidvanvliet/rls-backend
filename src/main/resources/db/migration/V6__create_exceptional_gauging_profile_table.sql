create table exceptional_gauging_profile
(
    id    serial not null
        constraint exceptional_gauging_profile_pkey
            primary key,
    value varchar(255)
);