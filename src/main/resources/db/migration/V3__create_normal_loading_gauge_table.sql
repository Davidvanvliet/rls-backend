create table normal_loading_gauge
(
    id          serial not null
        constraint normal_loading_gauge_pkey
            primary key,
    description varchar(255),
    value       varchar(255)
);