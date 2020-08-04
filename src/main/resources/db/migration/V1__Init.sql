create table anly.card (
    user_id integer not null,
    product_id integer not null
);

create table anly.category (
    id integer not null,
    name varchar(255),
    primary key (id)
);

create table anly.child_message (
    id integer not null,
    text varchar(255),
    parent_message_id integer,
    user_id integer,
    primary key (id)
);

create table anly.desired (
    user_id integer not null,
    product_id integer not null
);

create table anly.hibernate_sequence (
    next_val bigint
);

insert into anly.hibernate_sequence values ( 1 );
insert into anly.hibernate_sequence values ( 1 );
insert into anly.hibernate_sequence values ( 1 );
insert into anly.hibernate_sequence values ( 1 );
insert into anly.hibernate_sequence values ( 1 );

create table anly.parent_message (
    id integer not null,
    rate tinyint not null,
    text varchar(255),
    product_id integer,
    user_id integer,
    primary key (id)
);

create table anly.product (
    id integer not null,
    description varchar(255),
    name varchar(255),
    price integer not null,
    category_id integer,
    primary key (id)
);

create table anly.product_images (
    product_id integer not null,
    image varchar(255)
);

create table anly.user_role (
    user_id integer not null,
    roles varchar(255)
);

create table anly.usr (
    id integer not null,
    activation_code varchar(255),
    email varchar(255),
    is_activated bit default(0),
    name varchar(255),
    password varchar(255),
    password_secret_code varchar(255),
    primary key (id)
);

alter table anly.card add constraint FK7rihxh4l9qg8xjeuhe5yoxy92 foreign key (product_id) references anly.product (id);

alter table anly.card add constraint FKfklks4pup2r1ni8v0g5in9ee8 foreign key (user_id) references anly.usr (id);

alter table anly.child_message add constraint FKkma2s6xew9roicmgksnvvapc3 foreign key (parent_message_id) references anly.parent_message (id);

alter table anly.child_message add constraint FK3fvttcxg7r6ta8jc7d4h9cuuo foreign key (user_id) references anly.usr (id);

alter table anly.desired add constraint FKqcd63iwra6iumoqjyggc2nyei foreign key (product_id) references anly.product (id);

alter table anly.desired add constraint FKo3orobee69qsn0m4io9qdbqsv foreign key (user_id) references anly.usr (id);

alter table anly.parent_message add constraint FKml2f0e5b4av8jral6uoq15xr7 foreign key (product_id) references anly.product (id);

alter table anly.parent_message add constraint FKj17b7p8pr3mjrn9k63p3x28bg foreign key (user_id) references anly.usr (id);

alter table anly.product add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references anly.category (id);

alter table anly.product_images add constraint FKi8jnqq05sk5nkma3pfp3ylqrt foreign key (product_id) references anly.product (id);

alter table anly.user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references anly.usr (id);