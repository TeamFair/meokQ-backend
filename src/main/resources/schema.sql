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
-- drop sequence if exists SEQ_CHALLENGE_ID;
-- CREATE SEQUENCE SEQ_CHALLENGE_ID START WITH 100;
-- drop sequence if exists SEQ_COUPON_ID;
-- CREATE SEQUENCE SEQ_COUPON_ID START WITH 100;
-- drop sequence if exists SEQ_IMAGE_ID;
-- CREATE SEQUENCE SEQ_IMAGE_ID START WITH 100;
-- drop sequence if exists SEQ_MARKET_ID;
-- CREATE SEQUENCE SEQ_MARKET_ID START WITH 100;


CREATE TABLE IF NOT EXISTS `batch_job_execution` (
                                                     `job_execution_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `version` BIGINT NOT NULL,
                                                     `job_instance_id` BIGINT NOT NULL,
                                                     `create_time` DATETIME NOT NULL,
                                                     `start_time` DATETIME DEFAULT NULL,
                                                     `end_time` DATETIME DEFAULT NULL,
                                                     `status` VARCHAR(10) NOT NULL,
    `exit_code` VARCHAR(2500) DEFAULT NULL,
    `exit_message` VARCHAR(2500) DEFAULT NULL,
    `last_updated` DATETIME DEFAULT NULL,
    PRIMARY KEY (`job_execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 배치 작업 실행 컨텍스트 테이블 생성
CREATE TABLE IF NOT EXISTS `batch_job_execution_context` (
                                                             `job_execution_id` BIGINT NOT NULL,
                                                             `key_name` VARCHAR(2500) NOT NULL,
    `string_val` VARCHAR(2500) DEFAULT NULL,
    `date_val` DATETIME DEFAULT NULL,
    `long_val` BIGINT DEFAULT NULL,
    `double_val` DOUBLE DEFAULT NULL,
    `identifying` VARCHAR(2500) NOT NULL,
    PRIMARY KEY (`job_execution_id`,`key_name`),
    CONSTRAINT `FK_bje_context` FOREIGN KEY (`job_execution_id`) REFERENCES `batch_job_execution` (`job_execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 배치 작업 인스턴스 테이블 생성
CREATE TABLE IF NOT EXISTS `batch_job_instance` (
                                                    `job_instance_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                    `version` BIGINT NOT NULL,
                                                    `job_name` VARCHAR(100) NOT NULL,
    `job_key` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`job_instance_id`),
    UNIQUE KEY `JOB_INST_UN` (`job_name`,`job_key`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 배치 단계 실행 테이블 생성
CREATE TABLE IF NOT EXISTS `batch_step_execution` (
                                                      `step_execution_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                      `version` BIGINT NOT NULL,
                                                      `step_name` VARCHAR(100) NOT NULL,
    `job_execution_id` BIGINT NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME DEFAULT NULL,
    `status` VARCHAR(10) NOT NULL,
    `commit_count` BIGINT NOT NULL,
    `read_count` BIGINT NOT NULL,
    `filter_count` BIGINT NOT NULL,
    `write_count` BIGINT NOT NULL,
    `read_skip_count` BIGINT NOT NULL,
    `write_skip_count` BIGINT NOT NULL,
    `process_skip_count` BIGINT NOT NULL,
    `rollback_count` BIGINT NOT NULL,
    `exit_code` VARCHAR(2500) DEFAULT NULL,
    `exit_message` VARCHAR(2500) DEFAULT NULL,
    `last_updated` DATETIME DEFAULT NULL,
    PRIMARY KEY (`step_execution_id`),
    KEY `JOB_EXECUTION_FK` (`job_execution_id`),
    CONSTRAINT `JOB_EXECUTION_FK` FOREIGN KEY (`job_execution_id`) REFERENCES `batch_job_execution` (`job_execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 배치 단계 실행 컨텍스트 테이블 생성
CREATE TABLE IF NOT EXISTS `batch_step_execution_context` (
                                                              `step_execution_id` BIGINT NOT NULL,
                                                              `key_name` VARCHAR(2500) NOT NULL,
    `string_val` VARCHAR(2500) DEFAULT NULL,
    `date_val` DATETIME DEFAULT NULL,
    `long_val` BIGINT DEFAULT NULL,
    `double_val` DOUBLE DEFAULT NULL,
    `identifying` VARCHAR(2500) NOT NULL,
    PRIMARY KEY (`step_execution_id`,`key_name`),
    CONSTRAINT `FK_bse_context` FOREIGN KEY (`step_execution_id`) REFERENCES `batch_step_execution` (`step_execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
