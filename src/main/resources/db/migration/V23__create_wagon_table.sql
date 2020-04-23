create table wagon
(
    id                       serial  not null
        constraint wagon_pkey
            primary key,
    owner_id                 integer,
    number_freight           varchar(255) UNIQUE,
    code                     varchar(255),
    brake_weightg            integer not null,
    brake_weightp            integer not null,
    length_over_buffers      integer not null,
    type_name                varchar(255),
    max_speed                integer not null,
    wagon_number_of_axles    integer not null,
    wagon_weight_empty       integer not null
);