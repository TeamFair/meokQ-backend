/*drop table if exists tb_market cascade;
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
    `type` varchar(255),
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
    `type` varchar(255),
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
);*/

/* sequence */
/*drop sequence if exists seq_notice;
create sequence seq_notice start with 100 increment by 1;
drop sequence if exists seq_market;
create sequence seq_market start with 200 increment by 1;
drop sequence if exists seq_quest;
create sequence seq_quest start with 300 increment by 1;*/
drop sequence if exists SEQ_CHALLENGE_ID;
CREATE SEQUENCE SEQ_CHALLENGE_ID START WITH 100;
drop sequence if exists SEQ_COUPON_ID;
CREATE SEQUENCE SEQ_COUPON_ID START WITH 100;
drop sequence if exists SEQ_IMAGE_ID;
CREATE SEQUENCE SEQ_IMAGE_ID START WITH 100;
drop sequence if exists SEQ_MARKET_ID;
CREATE SEQUENCE SEQ_MARKET_ID START WITH 100;

-- 배치 시스템 수동 테이블 추가

CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
                                     JOB_INSTANCE_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
                                     VERSION BIGINT ,
                                     JOB_NAME VARCHAR(100) NOT NULL,
                                     JOB_KEY VARCHAR(32) NOT NULL,
                                     constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION  (
                                      JOB_EXECUTION_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
                                      VERSION BIGINT  ,
                                      JOB_INSTANCE_ID BIGINT NOT NULL,
                                      CREATE_TIME TIMESTAMP(9) NOT NULL,
                                      START_TIME TIMESTAMP(9) DEFAULT NULL ,
                                      END_TIME TIMESTAMP(9) DEFAULT NULL ,
                                      STATUS VARCHAR(10) ,
                                      EXIT_CODE VARCHAR(2500) ,
                                      EXIT_MESSAGE VARCHAR(2500) ,
                                      LAST_UPDATED TIMESTAMP(9),
                                      constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
                                          references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_PARAMS  (
                                             JOB_EXECUTION_ID BIGINT NOT NULL ,
                                             PARAMETER_NAME VARCHAR(100) NOT NULL ,
                                             PARAMETER_TYPE VARCHAR(100) NOT NULL ,
                                             PARAMETER_VALUE VARCHAR(2500) ,
                                             IDENTIFYING CHAR(1) NOT NULL ,
                                             constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
                                                 references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION  (
                                       STEP_EXECUTION_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
                                       VERSION BIGINT NOT NULL,
                                       STEP_NAME VARCHAR(100) NOT NULL,
                                       JOB_EXECUTION_ID BIGINT NOT NULL,
                                       CREATE_TIME TIMESTAMP(9) NOT NULL,
                                       START_TIME TIMESTAMP(9) DEFAULT NULL ,
                                       END_TIME TIMESTAMP(9) DEFAULT NULL ,
                                       STATUS VARCHAR(10) ,
                                       COMMIT_COUNT BIGINT ,
                                       READ_COUNT BIGINT ,
                                       FILTER_COUNT BIGINT ,
                                       WRITE_COUNT BIGINT ,
                                       READ_SKIP_COUNT BIGINT ,
                                       WRITE_SKIP_COUNT BIGINT ,
                                       PROCESS_SKIP_COUNT BIGINT ,
                                       ROLLBACK_COUNT BIGINT ,
                                       EXIT_CODE VARCHAR(2500) ,
                                       EXIT_MESSAGE VARCHAR(2500) ,
                                       LAST_UPDATED TIMESTAMP(9),
                                       constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
                                           references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_CONTEXT  (
                                               STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                               SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                               SERIALIZED_CONTEXT LONGVARCHAR ,
                                               constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
                                                   references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_CONTEXT  (
                                              JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                              SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                              SERIALIZED_CONTEXT LONGVARCHAR ,
                                              constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
                                                  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE SEQUENCE IF NOT EXISTS BATCH_STEP_EXECUTION_SEQ;
CREATE SEQUENCE IF NOT EXISTS BATCH_JOB_EXECUTION_SEQ;
CREATE SEQUENCE IF NOT EXISTS BATCH_JOB_SEQ;
