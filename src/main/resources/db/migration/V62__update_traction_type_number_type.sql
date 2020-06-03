alter table traction
    alter column loco_type_number type bigint using loco_type_number::bigint;