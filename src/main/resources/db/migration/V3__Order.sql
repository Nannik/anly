create table anly.order (
    id integer not null,
    order_number integer not null,
    price integer not null,
    delivery_type varchar(255),
    status integer not null,
    user_id integer,
    primary key (id)
);

create table anly.order_product (
    order_id integer not null,
    product_id integer not null
);