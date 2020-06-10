insert into public.rolling_stock(stock_type, position, driver_indication, train_composition_id, traction_id) (
    SELECT 'traction',
           traction_in_train.position,
           traction_in_train.driver_indication,
           traction_in_train.train_composition_id,
           traction_in_train.traction_id
    from traction_in_train
);

insert into public.rolling_stock(stock_type, position, brake_type, total_load_weight, train_composition_id, wagon_id) (
    SELECT 'wagon',
           wagon_in_train.position,
           wagon_in_train.brake_type,
           wagon_in_train.total_load_weight,
           wagon_in_train.train_composition_id,
           wagon_in_train.wagon_id
    from wagon_in_train
);

drop table traction_in_train;

drop table wagon_in_train CASCADE;