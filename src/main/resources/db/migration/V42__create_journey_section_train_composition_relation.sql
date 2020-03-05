alter table train_composition
    add constraint fk_train_composition_journey_section foreign key (journey_section_id) references journey_section (id);