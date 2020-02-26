create table responsibility
(
    id               serial not null
        constraint responsibility_pkey
            primary key,
    owner_id         integer,
    responsibleim_id integer
        constraint fk_responsibility_im
            references company,
    responsibleru_id integer
        constraint fk_responsibility_ru
            references company
);