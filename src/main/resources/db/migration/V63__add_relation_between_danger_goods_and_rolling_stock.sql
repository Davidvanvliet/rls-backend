alter table danger_goods_in_wagon
    add constraint fk_danger_goods_in_wagon_rolling_stock
        foreign key (wagon_in_train_id) references rolling_stock;