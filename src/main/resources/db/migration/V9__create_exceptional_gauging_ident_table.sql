create table exceptional_gauging_ident
(
    id                          serial  not null
        constraint exceptional_gauging_ident_pkey
            primary key,
    im_partner                  integer not null,
    exceptional_gauging_code_id integer
        constraint fk_exceptional_gauging_ident_code
            references exceptional_gauging_code
);