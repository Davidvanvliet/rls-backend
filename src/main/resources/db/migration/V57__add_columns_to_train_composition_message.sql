alter table train_composition_message
    add column object_type varchar(255);

alter table train_composition_message
    add column start_date timestamp;

alter table train_composition_message
    add column timetable_year int4;

alter table train_composition_message
    add column variant varchar(255);

alter table train_composition_message
    add column company_id int4;

alter table train_composition_message
    add constraint fk_tcm_company
        foreign key (company_id)
            references company