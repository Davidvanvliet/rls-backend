create table wagon
(
    id                       serial  not null
        constraint wagon_pkey
            primary key,
    owner_id                 integer,
    code                     varchar(255),
    hand_brake_braked_weight integer not null,
    length_over_buffers      integer not null,
    name                     varchar(255),
    number_freight           varchar(255),
    wagon_number_of_axles    integer not null,
    wagon_weight_empty       integer not null
);