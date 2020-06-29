alter table owner
    add constraint fk_owner_company
        foreign key (company_id) references company;