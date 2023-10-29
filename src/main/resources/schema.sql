drop table if exists tb_admin cascade;
drop table if exists tb_business_hour cascade;
drop table if exists tb_challenge_history cascade;
drop table if exists tb_coupon cascade;
drop table if exists tb_customer cascade;
drop table if exists tb_image cascade;
drop table if exists tb_management cascade;
drop table if exists tb_market cascade;
drop table if exists tb_market_manager cascade;
drop table if exists tb_mission cascade;
drop table if exists tb_notice cascade;
drop table if exists tb_quest cascade;
drop table if exists tb_reward cascade;
    
create table tb_admin (
    delete_yn tinyint check (delete_yn between 0 and 1),
    status tinyint check (status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    admin_id varchar(255) not null,
    name varchar(255),
    nickname varchar(255),
    primary key (admin_id)
);
    
create table tb_business_hour (
    close_time time(6),
    day_week tinyint not null check (day_week between 0 and 6),
    delete_yn tinyint check (delete_yn between 0 and 1),
    open_time time(6),
    sequence integer not null,
    create_date timestamp(6),
    update_date timestamp(6),
    market_id varchar(255) not null,
    primary key (day_week, sequence, market_id)
);
    
create table tb_challenge_history (
    delete_yn tinyint check (delete_yn between 0 and 1),
    quest_id integer not null,
    sequence integer not null,
    status tinyint check (status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    customer_id varchar(255) not null,
    primary key (quest_id, sequence, customer_id)
);
    
create table tb_coupon (
    delete_yn tinyint check (delete_yn between 0 and 1),
    reward_sequence integer,
    status tinyint check (status between 0 and 2),
    create_date timestamp(6),
    expiry_date timestamp(6),
    update_date timestamp(6),
    use_date timestamp(6),
    coupon_id varchar(255) not null,
    customer_id varchar(255),
    quest_id varchar(255),
    primary key (coupon_id)
);
    
create table tb_customer (
    delete_yn tinyint check (delete_yn between 0 and 1),
    status tinyint check (status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    customer_id varchar(255) not null,
    name varchar(255),
    nickname varchar(255),
    primary key (customer_id)
);
    
create table tb_image (
    delete_yn tinyint check (delete_yn between 0 and 1),
    type tinyint check (type between 0 and 0),
    create_date timestamp(6),
    update_date timestamp(6),
    image_id varchar(255) not null,
    image_url varchar(255),
    notice_id integer,
    primary key (image_id)
);
    
create table tb_management (
    delete_yn tinyint check (delete_yn between 0 and 1),
    create_date timestamp(6),
    update_date timestamp(6),
    manager_id varchar(255) not null,
    market_id varchar(255) not null,
    primary key (manager_id, market_id)
);
    
create table tb_market (
    delete_yn tinyint check (delete_yn between 0 and 1),
    ticket_count integer not null,
    create_date timestamp(6),
    update_date timestamp(6),
    address varchar(255),
    content varchar(255),
    district varchar(255),
    market_id varchar(255) not null,
    name varchar(255),
    phone varchar(255),
    president_id varchar(255),
    primary key (market_id)
);
    
create table tb_market_manager (
    delete_yn tinyint check (delete_yn between 0 and 1),
    status tinyint check (status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    manager_id varchar(255) not null,
    name varchar(255),
    nickname varchar(255),
    position varchar(255),
    primary key (manager_id)
);
    
create table tb_mission (
    count integer,
    delete_yn tinyint check (delete_yn between 0 and 1),
    sequence integer not null,
    create_date timestamp(6),
    update_date timestamp(6),
    content varchar(255),
    quest_id varchar(255) not null,
    target varchar(255),
    primary key (sequence, quest_id)
);
    
create table tb_notice (
   delete_yn tinyint check (delete_yn between 0 and 1),
   target tinyint check (target between 0 and 3),
   create_date timestamp(6),
   update_date timestamp(6),
   content varchar(255),
   notice_id varchar(255) not null,
   title varchar(255),
   primary key (notice_id)
);
    
create table tb_quest (
    delete_yn tinyint check (delete_yn between 0 and 1),
    quest_status tinyint check (quest_status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    market_id varchar(255),
    quest_id varchar(255) not null,
    primary key (quest_id)
);
    
create table tb_reward (
    count integer,
    delete_yn tinyint check (delete_yn between 0 and 1),
    sequence integer not null,
    create_date timestamp(6),
    update_date timestamp(6),
    content varchar(255),
    discount_rate varchar(255),
    quest_id varchar(255) not null,
    target varchar(255),
    primary key (sequence, quest_id)
);

/* sequence */
drop sequence if exists notice_sequence;
create sequence notice_sequence start with 100 increment by 1;

