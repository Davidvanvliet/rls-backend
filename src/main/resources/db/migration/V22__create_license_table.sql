create table license
(
    id         serial not null
        constraint license_pkey
            primary key,
    contract   varchar(255),
    valid_from timestamp,
    valid_to   timestamp,
    owner_id   integer
        constraint fk_license_owner
            references owner
);