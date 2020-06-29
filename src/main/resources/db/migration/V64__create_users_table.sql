create table public.users
(
    user_id varchar(255) not null
        constraint users_pkey
            primary key,
    owner_id integer
        constraint fk__users_owner
            references public.owner
);

