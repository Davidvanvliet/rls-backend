create table train
(
    id                                   serial  not null
        constraint train_pkey
            primary key,
    owner_id                             integer,
    operational_train_number             varchar(255),
    scheduled_date_time_at_transfer      timestamp,
    scheduled_time_at_handover           timestamp,
    train_type                           integer not null,
    transfer_point_location_primary_code integer
        constraint fk_train_transfer_point_location_primary_code
            references location,
    transfereeim_id                      integer
        constraint fk_train_transferee_im
            references company
);

alter table train
    owner to postgres;

