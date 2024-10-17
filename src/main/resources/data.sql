INSERT INTO tb_market (market_id, `name`, address, district, content, phone, ticket_count, logo_image_id, status, president_id)
VALUES
    ('MK00000001', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0211111111', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000002', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0222222222', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000003', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0233333333', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000004', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0244444444', 10, 'IMMA2024031114564633', 'UNDER_REVIEW', 'BS10000001'),
    ('MK00000005', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0255555555', 10, 'IMMA2024031114564633', 'UNDER_REVIEW', 'BS10000002'),
    ('MK00000010', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0255555555', 10, 'IMMA2024031114564633', 'REGISTERED', 'BS10000002');
;

INSERT INTO tb_reward (reward_id, quest_id, quantity, discount_rate ,content, target, `type`, create_date)
VALUES
    ('RW00000004', '832a1c95-c300-471a-919e-0e767978e1e2', 50, null, 'STRENGTH','XP', 'XP', NOW()),
    ('RW00000005', '8d21793d-261f-4c78-b140-0296e169e6a0', 50, null, 'STRENGTH','XP', 'XP', NOW()),
    ('RW00000001', 'a2b01530-7d17-4178-857b-35a5d4d7e2d6', 5, null, null,'COFFEE', 'DISCOUNT', NOW()),
    ('RW00000002', '58cc11d5-b4c7-4762-b7a0-67b001e40272', 3, null, null,'DONUT', 'GIFT', NOW()),
    ('RW00000003', 'efc2b619-8754-4f79-88c3-0136cbf57d58', 10, null, null,'COFFEE', 'DISCOUNT', NOW())
;

INSERT INTO tb_mission (mission_id, quest_id, quantity, content, target, `type`, create_date)
VALUES
    ('MS00000004', '832a1c95-c300-471a-919e-0e767978e1e2', 50, '태국 음식 시도하기', 'XP', 'NORMAL', NOW()),
    ('MS00000005', '8d21793d-261f-4c78-b140-0296e169e6a0', 10, '나만의 편의점 레시피 사진 찍기', 'TEA', 'NORMAL', NOW()),
    ('MS00000001', 'a2b01530-7d17-4178-857b-35a5d4d7e2d6', 5, '범죄도시1 10번 보기 ', 'XP', 'NORMAL', NOW()),
    ('MS00000002', '58cc11d5-b4c7-4762-b7a0-67b001e40272', 3, '소나무 사진 찍기', 'XP', 'NORMAL', NOW()),
    ('MS00000003', 'efc2b619-8754-4f79-88c3-0136cbf57d58', 10, '5번 이상 방문', null, 'FREE', NOW())
;

-- Insert data into tb_image table
INSERT INTO tb_image (file_id, `type`, `size`,`is_deleted`)
VALUES
    ('IMMA2024031114590814', 'BUSINESS_REGISTRATION_CERTIFICATE', 1024, false),
    ('IMMA2024031114593704', 'ID_CARD', 2048,false),
    ('IMMA2024031114564633', 'MARKET_LOGO', 3072,false),
    ('IMMA2024031115000583', 'RECEIPT', 3072,false)
;

-- Insert data into tb_customer table

INSERT INTO tb_customer(customer_id, create_date, update_date, nickname_seq, channel, email, nickname, status, withdraw_at)
VALUES
    ('110804aa-a3f9-4894-93d9-9b446e583b27', '2024-01-30 12:43:05.796058', '2024-01-30 12:43:05.796078',1, 'APPLE', 'dsss@sample.com', 'USER2024013012430596', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4615', '2024-02-08 15:10:39.177130', '2024-02-10 23:32:44.243646',2, 'APPLE', 'dlwlsgml8847@sample.com', 'USER2024020815103834', 'ACTIVE', '2024-01-30 00:35:30.949920'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f60', '2024-02-14 14:33:10.481530', '2024-02-14 14:33:10.481567',2, 'APPLE', 'wlsgml8847@sample.com', 'USER2024021414331002', 'ACTIVE', '2024-01-30 00:35:30.949920'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456454', '2024-03-05 14:12:32.579302', '2024-03-05 14:12:32.579316',2, 'KAKAO', 'email@sample.com', 'USER2024030514123269', 'DORMANT', '2024-01-30 00:35:30.949920'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b8b1', '2024-02-05 13:11:35.501047', '2024-02-05 13:11:35.501083',2, 'APPLE', '8847wlsgml@naver.com', 'USER2024020513113554', 'DORMANT', '2024-01-30 00:35:30.949920'),
    ('CS10000001', '2024-02-05 13:11:35.501047', '2024-02-05 13:11:35.501083',2, 'APPLE', '8337wlsgml@naver.com', 'USER2024020513113551', 'ACTIVE', '2024-01-30 00:35:30.949920'),
    ('CS10000002', '2024-02-05 13:11:35.501047', '2024-02-05 13:11:35.501083',2, 'APPLE', '22222lsgml@naver.com', 'USER2024020513222222', 'ACTIVE', '2024-01-30 00:35:30.949920'),
    ('CS10000003', NOW(), NOW(),0, 'APPLE', 'test1@naver.com', '그레이스토크', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000004', NOW(), NOW(),0, 'APPLE', 'test2@naver.com', '소상우사', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000005', NOW(), NOW(),0, 'APPLE', 'test3@naver.com', 'Metron', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000006', NOW(), NOW(),0, 'APPLE', 'test4@naver.com', 'Canton', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000007', NOW(), NOW(),0, 'APPLE', 'test5@naver.com', '발렌타', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000008', NOW(), NOW(),0, 'APPLE', 'test6@naver.com', '라비', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000009', NOW(), NOW(),0, 'APPLE', 'test7@naver.com', '프로토', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000010', NOW(), NOW(),0, 'APPLE', 'test8@naver.com', '실로', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000011', NOW(), NOW(),0, 'APPLE', 'test9@naver.com', '로져리우스', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000012', NOW(), NOW(),0, 'APPLE', 'test10@naver.com', '별님', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000013', NOW(), NOW(),0, 'APPLE', 'test11@naver.com', '채식', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000014', NOW(), NOW(),0, 'APPLE', 'test12@naver.com', '에니오푸스', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000015', NOW(), NOW(),0, 'APPLE', 'test13@naver.com', 'Kapabas_a', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000016', NOW(), NOW(),0, 'APPLE', 'test14@naver.com', '크나이스', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000017', NOW(), NOW(),0, 'APPLE', 'test15@naver.com', '지도', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000018', NOW(), NOW(),0, 'APPLE', 'test16@naver.com', 'Karia', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000019', NOW(), NOW(),0, 'APPLE', 'test17@naver.com', 'Marciana', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000020', NOW(), NOW(),0, 'APPLE', 'test18@naver.com', '소프티모', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000021', NOW(), NOW(),0, 'APPLE', 'test19@naver.com', '마리노', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000022', NOW(), NOW(),0, 'APPLE', 'test20@naver.com', 'Audenin', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000023', NOW(), NOW(),0, 'APPLE', 'test21@naver.com', '뽀야', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000024', NOW(), NOW(),0, 'APPLE', 'test22@naver.com', 'VLAD', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000025', NOW(), NOW(),0, 'APPLE', 'test23@naver.com', '사이즈', 'ACTIVE', '2024-03-20 00:35:30.949920'),
    ('CS10000026', NOW(), NOW(),0, 'APPLE', 'test24@naver.com', '귀랑', 'ACTIVE', '2024-03-20 00:35:30.949920')

;

-- Insert data into tb_quest table
INSERT INTO tb_quest (`quest_id`, `create_date`, `update_date`, `expire_date`, `market_id`, `status`,`creator_role`,`writer`,`score`,`image_id`)
VALUES
    ('832a1c95-c300-471a-919e-0e767978e1e2', '2024-03-04 18:27:28.999295', '2024-03-04 18:43:11.306922', '2024-04-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','요리탐험가(일상)',0,'IMMA2024031114590811'),
    ('8d21793d-261f-4c78-b140-0296e169e6a0', '2024-02-21 10:53:17.837651', '2024-02-25 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'UNDER_REVIEW','ADMIN','놀이공원탐험가(일상)',0,'IMMA2024031114590815'),
    ('a2b01530-7d17-4178-857b-35a5d4d7e2d6', '2024-02-23 10:53:17.837651', '2024-03-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','일상탐험가(일상)',0,'IMMA2024031114590812'),
    ('58cc11d5-b4c7-4762-b7a0-67b001e40272', '2024-02-24 10:53:17.837651', '2024-04-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','상상탐험가(일상)',0,'IMMA2024031114590815'),
    ('efc2b619-8754-4f79-88c3-0136cbf57d58', '2024-02-26 10:53:17.837651', '2024-05-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','유럽탐험가(일상)',0,'IMMA2024031114590817'),
    ('bdf20dca-8d59-4c84-bfb9-5465cabd4eef', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590818'),
    ('a1f1ac10-9dcd-4a62-bbef-3e0ab69b7bfe', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590819'),
    ('a30b85b1-1f4c-44b4-9462-0b6040845e52', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590816'),
    ('c801b910-0eba-48a8-8293-2ce89473d5a4', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590814'),
    ('fb0e5aae-edc6-4a9e-9d1e-3fbfd14f134c', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590814'),
    ('2e1702a3-5e40-44bd-a557-8bad3330d5ec', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590813'),
    ('63312ed3-9cb6-493e-8f2a-3262ec5d961a', '2024-02-28 10:53:17.837651', '2024-06-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'PUBLISHED','ADMIN','서울탐험가(일상)',0,'IMMA2024031114590812'),
    ('e05f6e49-cb96-4d8d-9c3a-933f9bc5d3a5', '2024-03-21 10:53:17.837651', '2024-07-21 10:53:17.837669', '2024-09-01 18:43:11.284738', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', 'UNDER_REVIEW','BOSS','일본탐험가(일상)',0,'IMMA2024031114590818')
;

-- Insert data into tb_challenge_history table
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, quest_id, receipt_image_id,like_emoji_cnt,hate_emoji_cnt,create_date,view_count)
VALUES
    ('CH10000001', 'CS10000001', 'UNDER_REVIEW', NULL, '832a1c95-c300-471a-919e-0e767978e1e2', 'IMMA2024031114590814',0,0,'2024-03-04 18:27:28.999295',0),
    ('CH10000002', 'CS10000001', 'UNDER_REVIEW', NULL, '8d21793d-261f-4c78-b140-0296e169e6a0', 'IMMA2024031114593704',0,0,'2024-03-04 18:27:28.999295',0),
    ('CH10000003', 'CS10000002', 'APPROVED', NULL, '8d21793d-261f-4c78-b140-0296e169e6a0', 'IMMA2024031114564633',0,0,'2024-03-04 18:27:28.999295',0),
    ('5660fea4-6596-407c-946d-dbc3c926eb56', 'CS10000001', 'APPROVED', NULL, '8d21793d-261f-4c78-b140-0296e169e6a0', 'IMMA2024031114593704',0,0,'2024-03-04 18:27:28.999295',0),
    ('1a1435c3-8695-45e0-aba2-05365eade0d3', 'CS10000001', 'APPROVED', NULL, '8d21793d-261f-4c78-b140-0296e169e6a0', 'IMMA2024031114593704',0,0,'2024-03-04 18:27:28.999295',0),
    ('b391d3e2-f9fa-4c54-94df-5aebce941d41', 'CS10000001', 'APPROVED', NULL, '8d21793d-261f-4c78-b140-0296e169e6a0', 'IMMA2024031114593704',0,0,'2024-03-04 18:27:28.999295',0)
;

-- Insert data into tb_boss table
INSERT INTO tb_boss (boss_id, status, email)
VALUES
    ('BS10000001', 'ACTIVE', 'user1@example.com'),
    ('BS10000002', 'DORMANT', 'user2@example.com')
;

INSERT INTO tb_coupon (`coupon_id`, `create_date`, `update_date`, `challenge_id`, `expire_date`, `market_id`, `quest_id`, `reward_id`, `status`, `use_date`, `user_id`) VALUES
    ('05ba541a-e1a7-49b2-bc51-441906baf9f2', '2024-03-19 12:07:56.358356', '2024-03-19 12:07:56.358384', '2c54d6b4-4940-410a-95af-1a396b304ea5', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('254fa599-802c-4f9b-a9b6-094a9712ed3b', '2024-03-20 00:26:06.552195', '2024-03-20 00:26:06.552213', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('2d13f281-9ae3-4186-81b1-f9790978e639', '2024-03-19 13:53:28.203559', '2024-03-19 13:53:28.203573', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('78927cc4-c5da-4755-bd0f-674a8f10330f', '2024-03-20 11:19:33.555409', '2024-03-20 11:19:33.555426', '5660fea4-6596-407c-946d-dbc3c926eb56', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('b2d11f2f-9a95-48d5-8175-acf4d9317787', '2024-03-19 13:54:00.292187', '2024-03-19 13:54:00.292205', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('e7824429-3cba-462d-a908-54f5db2b222e', '2024-03-20 00:35:30.949920', '2024-03-20 00:35:30.949947', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
    ('f0dcaf61-1f73-4847-bd21-df0dccb9c55b', '2024-03-19 16:25:19.477983', '2024-03-19 16:25:19.478008', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454')
;

INSERT INTO tb_market_time (week_day, market_id, open_time, close_time, holiday_yn) VALUES
    ('MON', 'MK00000001', '09:00', '20:00', 'N'),
    ('TUE', 'MK00000001', '09:00', '18:00', 'N'),
    ('WED', 'MK00000001', '09:00', '18:00', 'N'),
    ('THU', 'MK00000001', '09:00', '18:00', 'N'),
    ('FRI', 'MK00000001', '09:00', '18:00', 'N'),
    ('SAT', 'MK00000001', '09:00', '18:00', 'N'),
    ('SUN', 'MK00000001', '09:00', '20:00', 'Y'),
    ('MON', 'MK00000002', '10:00', '20:00', 'N'),
    ('TUE', 'MK00000002', '10:00', '18:00', 'N'),
    ('WED', 'MK00000002', '09:00', '18:00', 'N'),
    ('THU', 'MK00000002', '09:00', '18:00', 'N'),
    ('FRI', 'MK00000002', '09:00', '18:00', 'N'),
    ('SAT', 'MK00000002', '09:00', '18:00', 'N'),
    ('SUN', 'MK00000002', '10:00', '20:00', 'Y'),
    ('MON', 'MK00000003', '10:00', '20:00', 'N'),
    ('TUE', 'MK00000003', '10:00', '18:00', 'N'),
    ('WED', 'MK00000003', '09:00', '18:00', 'N'),
    ('THU', 'MK00000003', '09:00', '18:00', 'N'),
    ('FRI', 'MK00000003', '09:00', '18:00', 'N'),
    ('SAT', 'MK00000003', '09:00', '18:00', 'N'),
    ('SUN', 'MK00000003', '10:00', '20:00', 'Y')
;

-- Insert data into tb_operator_info
INSERT INTO tb_operator_info (record_id, owner_name, owner_birthdate, idcard_image_id)
VALUES
    ('PR00000001', 'John Doe', '19900101', 'IMMA2024031114593704'),
    ('PR00000002', 'Jane Smith', '19850515', 'IMMA2024031114593704'),
    ('PR00000003', 'Bob Johnson', '19780930', 'IMMA2024031114593704')
;

-- Insert data into tb_business_report
INSERT INTO tb_license_business (record_id, license_id, license_image_id, owner_name, market_name, address, postal_code)
VALUES
    ('BR00000001', 'LICENSE123', 'IMMA2024031114590814', 'John Doe', 'Doe Market', '123 Main St', '12345'),
    ('BR00000002', 'LICENSE456', 'IMMA2024031114590814', 'Jane Smith', 'Smith Market', '456 Oak St', '67890'),
    ('BR00000003', 'LICENSE789', 'IMMA2024031114590814', 'Bob Johnson', 'Johnson Market', '789 Elm St', '34567')
;

-- Insert data into tb_market_auth_history
INSERT INTO tb_market_auth_history (record_id, market_id, review_result, comment, operator_info_id, license_info_id)
VALUES
    ('MA00000001', 'MK00000001', 'APPROVED', 'Good market', 'PR00000001', 'BR00000001'),
    ('MA00000002', 'MK00000002', 'REJECTED', 'Not suitable', 'PR00000002', 'BR00000002'),
    ('MA00000003', 'MK00000003', null, null, 'PR00000003', 'BR00000003')
;


-- Insert data into tb_notice with different targets
INSERT INTO tb_notice (notice_id, title, content, target)
VALUES
    ('NT00000001', 'Customer Notice', 'Content for customers', 'CUSTOMER'),
    ('NT00000002', 'Boss Notice', 'Content for bosses', 'BOSS'),
    ('NT00000003', 'Admin Notice', 'Content for admins', 'ADMIN'),
    ('NT00000004', 'General Notice', 'Content for everyone', 'ALL'),
    ('NT00000005', 'Another Customer Notice', 'More content for customers', 'CUSTOMER'),
    ('NT00000006', 'Another Admin Notice', 'More content for admins', 'ADMIN')
;

-- Customer 약관 동의 내역 삽입
INSERT INTO tb_agreement_history (agreement_id, user_id, agreement_type, version, accept_yn, create_date)
VALUES
    ('1', 'CS10000001', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('2', 'CS10000001', 'PRIVACY_POLICY_CUSTOMER', 1, 'N',now()),
    ('3', 'CS10000001', 'PRIVACY_CONSENT_FORM_CUSTOMER', 1, 'Y',now()),
    ('4', 'CS10000002', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('5', 'CS10000002', 'PRIVACY_POLICY_CUSTOMER', 1, 'N',now()),
    ('6', 'CS10000002', 'PRIVACY_CONSENT_FORM_CUSTOMER', 1, 'Y',now()),
    ('7', 'CS10000003', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('8', 'CS10000004', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('9', 'CS10000005', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('10', 'CS10000006', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now()),
    ('11', 'CS10000007', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now())
;

-- Boss 약관 동의 내역 삽입
INSERT INTO tb_agreement_history (agreement_id, user_id, agreement_type, version, accept_yn, create_date)
VALUES
    ('12', 'BS10000001', 'TERMS_OF_SERVICE_BOSS', 1, 'Y', now()),
    ('13', 'BS10000001', 'UNIQUEID_CONSENT_FORM_BOSS', 1, 'N',now()),
    ('14', 'BS10000001', 'PRIVACY_POLICY_BOSS', 1, 'Y',now()),
    ('15', 'BS10000002', 'TERMS_OF_SERVICE_BOSS', 1, 'Y',now()),
    ('16', 'BS10000002', 'UNIQUEID_CONSENT_FORM_BOSS', 1, 'N',now()),
    ('17', 'BS10000002', 'PRIVACY_POLICY_BOSS', 1, 'Y',now())
;

-- 이모지 생성
INSERT INTO tb_emoji (emoji_id, status, user_id, target_type, target_id)
VALUES
    ('b4e8ac10-9a8b-4c12-abcd-3b2a1c9d8e7f', 'LIKE', '110804aa-a3f9-4894-93d9-9b446e583b27', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('a1f1ac10-9dcd-4a62-bbef-3e0ab69b7bfe', 'HATE', '110804aa-a3f9-4894-93d9-9b446e583b27', 'CHALLENGE', '1a1435c3-8695-45e0-aba2-05365eade0d3'),
    ('b2b5ac10-4e9a-4f42-b0a4-9d8b63d34e7c', 'LIKE', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('c3e6ac10-8e7b-4d32-abc1-3c4e5d6f7a8b', 'LIKE', '82eb81c2-7df9-4e47-9362-c71c6ac78f60', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('d4f3ac10-1a2b-4e82-cdef-4a5b6c7d8e9f', 'LIKE', 'f6744202-f40f-4ce7-b00f-1a8d10456454', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('e5b8ac10-9a8b-4c12-abcd-3b2a1c9d8e7f', 'HATE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('f6e9ac10-9a8b-4c12-abcd-3b2a1c9d8e7f', 'LIKE', '110804aa-a3f9-4894-93d9-9b446e583b27', 'CHALLENGE', '1a1435c3-8695-45e0-aba2-05365eade0d3'),
    ('07a1ac10-84cd-4a62-bbef-3e0ab69b7bfe', 'LIKE', '110804aa-a3f9-4894-93d9-9b446e583b27', 'CHALLENGE', '1a1435c3-8695-45e0-aba2-05365eade0d3'),
    ('18b2ac10-3e9a-4f42-b0a4-9d8b63d34e7c', 'LIKE', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('29e3ac10-8e7b-4d32-abc1-3c4e5d6f7a8b', 'LIKE', '82eb81c2-7df9-4e47-9362-c71c6ac78f60', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('3af4ac10-1a2b-4e82-cdef-4a5b6c7d8e9f', 'LIKE', 'f6744202-f40f-4ce7-b00f-1a8d10456454', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('4bf5ac10-4375-4e6f-b359-abaa3c07b295', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('5cf6ac10-7428-4d4b-8ae1-412d6b5a49e5', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('6df7ac10-1538-47d9-b6a3-b2cf7a39d131', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('7ef8ac10-cff5-45d2-a2f9-6e8ac90af177', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('8ff9ac10-ef68-4be9-8a37-d3ac6699cc9b', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', '5660fea4-6596-407c-946d-dbc3c926eb56'),
    ('90faac10-dbc5-4a49-a1eb-bfe41c09f472', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', 'b391d3e2-f9fa-4c54-94df-5aebce941d41'),
    ('a1fbac10-fc98-47ba-b450-9385e8ddb685', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', 'b391d3e2-f9fa-4c54-94df-5aebce941d41'),
    ('b2fcac10-f12f-4a99-b8ef-3423eda447b3', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', 'b391d3e2-f9fa-4c54-94df-5aebce941d41'),
    ('c3fdac10-50e5-4e28-a482-4b5011940ebb', 'LIKE', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1', 'CHALLENGE', 'b391d3e2-f9fa-4c54-94df-5aebce941d41')
;

INSERT INTO tb_quest_history (quest_history_id, quest_id, customer_id)
VALUES
    ('be7c8c9f-695b-4950-bd68-eb5e49f39c58','8d21793d-261f-4c78-b140-0296e169e6a0', '110804aa-a3f9-4894-93d9-9b446e583b27'),
    ('f288fd27-20b4-4a0d-9d1a-220ef0bf9d6d','a1f1ac10-9dcd-4a62-bbef-3e0ab69b7bfe', '110804aa-a3f9-4894-93d9-9b446e583b27'),
    ('6f5a6bb3-4b6b-4286-bb77-d03bf17e7a2f','a2b01530-7d17-4178-857b-35a5d4d7e2d6', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('cc80c8f1-2775-4ec4-8f50-7d8c23f2eac8','58cc11d5-b4c7-4762-b7a0-67b001e40272', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('a8e65460-58f5-494a-9a5e-1d81c6d372f2','efc2b619-8754-4f79-88c3-0136cbf57d58', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615')
;

INSERT INTO tb_xp (xp_id, xp_point, xp_type, customer_id)
VALUES
    ('f2b5e7d1-8e1f-4c57-9e2d-4b7c4b0d3f9a', 150, 'STRENGTH', 'CS10000001'),
    ('f2b5e7d1-8e1f-4c57-9e2d-4b7c4b0d3f23', 0, 'INTELLECT', 'CS10000001'),
    ('f2b5e7d1-8e1f-4c57-9e2d-4b7c4b0d3ffj', 0, 'FUN', 'CS10000001'),
    ('f2b5e7d1-8e1f-4c57-9e2d-4b7c4b0d3f28', 0, 'CHARM', 'CS10000001'),
    ('f2b5e7d1-8e1f-4c57-9e2d-4b7c4b0d3fa9', 0, 'SOCIABILITY', 'CS10000001'),

    ('110804aa-a3f9-4894-93d9-9b446e583bd2', 200, 'STRENGTH', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('110804aa-a3f9-4894-93d9-9b446e583b42', 50, 'INTELLECT', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('110804aa-a3f9-4894-93d9-9b446e583b52', 30, 'FUN', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('110804aa-a3f9-4894-93d9-9b446e583b43', 100, 'CHARM', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('110804aa-a3f9-4894-93d9-9b446e583b12', 80, 'SOCIABILITY', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),

    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4623', 300, 'STRENGTH', 'CS10000002'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4663', 60, 'INTELLECT', 'CS10000002'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4645', 40, 'FUN', 'CS10000002'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4624', 90, 'CHARM', 'CS10000002'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4652', 100, 'SOCIABILITY', 'CS10000002'),

    ('82eb81c2-7df9-4e47-9362-c71c6ac78f60', 150, 'STRENGTH', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f16', 120, 'INTELLECT', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f23', 80, 'FUN', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f34', 200, 'CHARM', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f35', 70, 'SOCIABILITY', '4e7b25a2-5c65-4de4-82f7-f0034f5d4615'),

    ('f6744202-f40f-4ce7-b00f-1a8d10456454', 180, 'STRENGTH', '82eb81c2-7df9-4e47-9362-c71c6ac78f60'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456473', 130, 'INTELLECT', '82eb81c2-7df9-4e47-9362-c71c6ac78f60'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456424', 90, 'FUN', '82eb81c2-7df9-4e47-9362-c71c6ac78f60'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456456', 120, 'CHARM', '82eb81c2-7df9-4e47-9362-c71c6ac78f60'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456423', 60, 'SOCIABILITY', '82eb81c2-7df9-4e47-9362-c71c6ac78f60'),

    ('feeb066f-a118-4dfd-a141-eb8d6f31b823', 160, 'STRENGTH', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b862', 200, 'INTELLECT', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b812', 120, 'FUN', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b845', 100, 'CHARM', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b825', 50, 'SOCIABILITY', 'feeb066f-a118-4dfd-a141-eb8d6f31b8b1'),
    ('xp-001', 90, 'STRENGTH', 'CS10000003'),
    ('xp-002', 160, 'INTELLECT', 'CS10000003'),
    ('xp-003', 50, 'FUN', 'CS10000003'),
    ('xp-004', 30, 'CHARM', 'CS10000003'),
    ('xp-005', 40, 'SOCIABILITY', 'CS10000003'),

    ('xp-006', 260, 'STRENGTH', 'CS10000004'),
    ('xp-007', 280, 'INTELLECT', 'CS10000004'),
    ('xp-008', 160, 'FUN', 'CS10000004'),
    ('xp-009', 450, 'CHARM', 'CS10000004'),
    ('xp-010', 50, 'SOCIABILITY', 'CS10000004'),

    ('xp-011', 20, 'STRENGTH', 'CS10000005'),
    ('xp-012', 20, 'INTELLECT', 'CS10000005'),
    ('xp-013', 20, 'FUN', 'CS10000005'),
    ('xp-014', 10, 'CHARM', 'CS10000005'),
    ('xp-015', 10, 'SOCIABILITY', 'CS10000005'),

    ('xp-016', 140, 'STRENGTH', 'CS10000006'),
    ('xp-017', 20, 'INTELLECT', 'CS10000006'),
    ('xp-018', 70, 'FUN', 'CS10000006'),
    ('xp-019', 210, 'CHARM', 'CS10000006'),
    ('xp-020', 70, 'SOCIABILITY', 'CS10000006'),

    ('xp-021', 50, 'STRENGTH', 'CS10000007'),
    ('xp-022', 200, 'INTELLECT', 'CS10000007'),
    ('xp-023', 460, 'FUN', 'CS10000007'),
    ('xp-024', 60, 'CHARM', 'CS10000007'),
    ('xp-025', 150, 'SOCIABILITY', 'CS10000007'),

    ('xp-026', 80, 'STRENGTH', 'CS10000008'),
    ('xp-027', 250, 'INTELLECT', 'CS10000008'),
    ('xp-028', 830, 'FUN', 'CS10000008'),
    ('xp-029', 10, 'CHARM', 'CS10000008'),
    ('xp-030', 270, 'SOCIABILITY', 'CS10000008'),

    ('xp-031', 30, 'STRENGTH', 'CS10000009'),
    ('xp-032', 60, 'INTELLECT', 'CS10000009'),
    ('xp-033', 60, 'FUN', 'CS10000009'),
    ('xp-034', 20, 'CHARM', 'CS10000009'),
    ('xp-035', 60, 'SOCIABILITY', 'CS10000009'),

    ('xp-036', 490, 'STRENGTH', 'CS10000010'),
    ('xp-037', 430, 'INTELLECT', 'CS10000010'),
    ('xp-038', 390, 'FUN', 'CS10000010'),
    ('xp-039', 160, 'CHARM', 'CS10000010'),
    ('xp-040', 290, 'SOCIABILITY', 'CS10000010'),

    ('xp-041', 120, 'STRENGTH', 'CS10000011'),
    ('xp-042', 290, 'INTELLECT', 'CS10000011'),
    ('xp-043', 90, 'FUN', 'CS10000011'),
    ('xp-044', 20, 'CHARM', 'CS10000011'),
    ('xp-045', 90, 'SOCIABILITY', 'CS10000011'),

    ('xp-046', 240, 'STRENGTH', 'CS10000012'),
    ('xp-047', 380, 'INTELLECT', 'CS10000012'),
    ('xp-048', 150, 'FUN', 'CS10000012'),
    ('xp-049', 150, 'CHARM', 'CS10000012'),
    ('xp-050', 70, 'SOCIABILITY', 'CS10000012'),

    ('xp-051', 320, 'STRENGTH', 'CS10000013'),
    ('xp-052', 700, 'INTELLECT', 'CS10000013'),
    ('xp-053', 550, 'FUN', 'CS10000013'),
    ('xp-054', 350, 'CHARM', 'CS10000013'),
    ('xp-055', 620, 'SOCIABILITY', 'CS10000013'),

    ('xp-056', 60, 'STRENGTH', 'CS10000014'),
    ('xp-057', 70, 'INTELLECT', 'CS10000014'),
    ('xp-058', 80, 'FUN', 'CS10000014'),
    ('xp-059', 40, 'CHARM', 'CS10000014'),
    ('xp-060', 80, 'SOCIABILITY', 'CS10000014'),

    ('xp-061', 150, 'STRENGTH', 'CS10000015'),
    ('xp-062', 310, 'INTELLECT', 'CS10000015'),
    ('xp-063', 180, 'FUN', 'CS10000015'),
    ('xp-064', 70, 'CHARM', 'CS10000015'),
    ('xp-065', 50, 'SOCIABILITY', 'CS10000015'),

    ('xp-066', 100, 'STRENGTH', 'CS10000016'),
    ('xp-067', 70, 'INTELLECT', 'CS10000016'),
    ('xp-068', 90, 'FUN', 'CS10000016'),
    ('xp-069', 100, 'CHARM', 'CS10000016'),
    ('xp-070', 60, 'SOCIABILITY', 'CS10000016'),

    ('xp-071', 170, 'STRENGTH', 'CS10000017'),
    ('xp-072', 240, 'INTELLECT', 'CS10000017'),
    ('xp-073', 70, 'FUN', 'CS10000017'),
    ('xp-074', 80, 'CHARM', 'CS10000017'),
    ('xp-075', 310, 'SOCIABILITY', 'CS10000017'),

    ('xp-076', 20, 'STRENGTH', 'CS10000018'),
    ('xp-077', 50, 'INTELLECT', 'CS10000018'),
    ('xp-078', 30, 'FUN', 'CS10000018'),
    ('xp-079', 30, 'CHARM', 'CS10000018'),
    ('xp-080', 20, 'SOCIABILITY', 'CS10000018'),

    ('xp-081', 370, 'STRENGTH', 'CS10000019'),
    ('xp-082', 570, 'INTELLECT', 'CS10000019'),
    ('xp-083', 480, 'FUN', 'CS10000019'),
    ('xp-084', 200, 'CHARM', 'CS10000019'),
    ('xp-085', 360, 'SOCIABILITY', 'CS10000019'),

    ('xp-086', 290, 'STRENGTH', 'CS10000020'),
    ('xp-087', 110, 'INTELLECT', 'CS10000020'),
    ('xp-088', 210, 'FUN', 'CS10000020'),
    ('xp-089', 230, 'CHARM', 'CS10000020'),
    ('xp-090', 200, 'SOCIABILITY', 'CS10000020'),

    ('xp-091', 60, 'STRENGTH', 'CS10000021'),
    ('xp-092', 70, 'INTELLECT', 'CS10000021'),
    ('xp-093', 40, 'FUN', 'CS10000021'),
    ('xp-094', 60, 'CHARM', 'CS10000021'),
    ('xp-095', 40, 'SOCIABILITY', 'CS10000021'),

    ('xp-096', 100, 'STRENGTH', 'CS10000022'),
    ('xp-097', 100, 'INTELLECT', 'CS10000022'),
    ('xp-098', 160, 'FUN', 'CS10000022'),
    ('xp-099', 60, 'CHARM', 'CS10000022'),
    ('xp-100', 140, 'SOCIABILITY', 'CS10000022'),

    ('xp-101', 580, 'STRENGTH', 'CS10000023'),
    ('xp-102', 720, 'INTELLECT', 'CS10000023'),
    ('xp-103', 440, 'FUN', 'CS10000023'),
    ('xp-104', 250, 'CHARM', 'CS10000023'),
    ('xp-105', 440, 'SOCIABILITY', 'CS10000023'),

    ('xp-106', 140, 'STRENGTH', 'CS10000024'),
    ('xp-107', 250, 'INTELLECT', 'CS10000024'),
    ('xp-108', 100, 'FUN', 'CS10000024'),
    ('xp-109', 220, 'CHARM', 'CS10000024'),
    ('xp-110', 100, 'SOCIABILITY', 'CS10000024'),

    ('xp-111', 530, 'STRENGTH', 'CS10000025'),
    ('xp-112', 410, 'INTELLECT', 'CS10000025'),
    ('xp-113', 120, 'FUN', 'CS10000025'),
    ('xp-114', 260, 'CHARM', 'CS10000025'),
    ('xp-115', 340, 'SOCIABILITY', 'CS10000025'),

    ('xp-116', 320, 'STRENGTH', 'CS10000026'),
    ('xp-117', 190, 'INTELLECT', 'CS10000026'),
    ('xp-118', 510, 'FUN', 'CS10000026'),
    ('xp-119', 150, 'CHARM', 'CS10000026'),
    ('xp-120', 120, 'SOCIABILITY', 'CS10000026')

;

INSERT INTO tb_xp_history (record_id,description, title, xp_point, user_id)
VALUES
    (1,null, '챌린지 등록', 50, 'CS10000001'),
    (2,null, '챌린지 등록', 70, 'CS10000001'),
    (3,null, '챌린지 등록', 30, 'CS10000001')
;