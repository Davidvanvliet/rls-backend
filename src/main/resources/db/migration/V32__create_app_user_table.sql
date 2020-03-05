create table app_user
(
    id         serial  not null
        constraint app_user_pkey
            primary key,
    email      varchar(255),
    enabled    boolean not null,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    username   varchar(255)
        constraint uk_username
            unique,
    owner_id   integer
        constraint fk_app_user_owner
            references owner,
    role_id    integer
        constraint fk_app_user_role
            references role
);