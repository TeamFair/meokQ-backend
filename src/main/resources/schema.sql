drop table if exists tb_market cascade;
create table tb_market (
    ticket_count integer,
    create_date timestamp(6),
    update_date timestamp(6),
    address varchar(255),
    content varchar(255),
    district varchar(255),
    logo_image varchar(255),
    market_id varchar(255) not null,
    president_id varchar(255),
    name varchar(255),
    phone varchar(255),
    status varchar(255),
    primary key (market_id)
);

drop table if exists tb_mission cascade;
create table tb_mission (
    quantity integer,
    mission_id varchar(255) not null,
    create_date timestamp(6),
    update_date timestamp(6),
    content varchar(255),
    quest_id varchar(255) not null,
    target varchar(255),
    primary key (mission_id, quest_id)
);

drop table if exists tb_notice cascade;
create table tb_notice (
   target tinyint check (target between 0 and 3),
   create_date timestamp(6),
   update_date timestamp(6),
   content varchar(255),
   notice_id varchar(255) not null,
   title varchar(255),
   primary key (notice_id)
);

drop table if exists tb_reward cascade;
create table tb_reward (
    quantity integer,
    reward_id varchar(255) not null,
    create_date timestamp(6),
    update_date timestamp(6),
    content varchar(255),
    discount_rate varchar(255),
    quest_id varchar(255) not null,
    target varchar(255),
    primary key (reward_id, quest_id)
);

drop table if exists tb_quest cascade;
create table tb_quest (
    delete_yn tinyint check (delete_yn between 0 and 1),
    quest_status tinyint check (quest_status between 0 and 2),
    create_date timestamp(6),
    update_date timestamp(6),
    market_id varchar(255),
    quest_id varchar(255) not null,
    primary key (quest_id)
);

/* sequence */
drop sequence if exists seq_notice;
create sequence seq_notice start with 100 increment by 1;
drop sequence if exists seq_market;
create sequence seq_market start with 200 increment by 1;
drop sequence if exists seq_quest;
create sequence seq_quest start with 300 increment by 1;