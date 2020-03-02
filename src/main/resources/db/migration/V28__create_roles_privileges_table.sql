create table roles_privileges
(
    role_id      integer not null
        constraint fk_rp_role
            references role,
    privilege_id integer not null
        constraint fk_rp_privilege
            references privilege
);