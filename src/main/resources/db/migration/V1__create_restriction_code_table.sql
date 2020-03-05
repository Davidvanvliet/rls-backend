create table restriction_code
(
    id          serial not null
        constraint restriction_code_pkey
            primary key,
    code        varchar(255),
    description varchar(255),
    forp        varchar(255)
);