create table composit_identifier_operational_type
(
	id serial not null
		constraint composit_identifier_operational_type_pkey
			primary key,
	owner_id integer,
	core varchar(255),
	object_type varchar(255),
	start_date timestamp,
	timetable_year integer not null,
	variant varchar(255),
	company_id integer
		constraint fk_composit_company
			references company,
	train_composition_message_id integer
		constraint fk_composit_train_composition_message
			references train_composition_message
);