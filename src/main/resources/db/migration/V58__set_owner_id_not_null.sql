alter table ci_message
    alter column owner_id set not null;

alter table composit_identifier_operational_type
    alter column owner_id set not null;

alter table journey_section
    alter column owner_id set not null;

alter table loco_type_number
    alter column owner_id set not null;

alter table responsibility
    alter column owner_id set not null;

alter table traction
    alter column owner_id set not null;

alter table train
    alter column owner_id set not null;

alter table train_composition
    alter column owner_id set not null;

alter table train_composition_message
    alter column owner_id set not null;

alter table uic_request
    alter column owner_id set not null;

alter table wagon
    alter column owner_id set not null;

alter table wagon_in_train
    alter column owner_id set not null;

alter table xml_message
    alter column owner_id set not null;