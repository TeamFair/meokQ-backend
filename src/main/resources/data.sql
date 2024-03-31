INSERT INTO tb_market (market_id, `name`, address, district, content, phone, ticket_count, logo_image_id, status, president_id)
VALUES
    ('MK00000001', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0211111111', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000002', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0222222222', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000003', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0233333333', 10, 'IMMA2024031114564633', 'APPROVED', 'BS10000001'),
    ('MK00000004', 'Market Name', '서울특별시 종로구', '1100000000', 'Market Content', '0244444444', 10, 'IMMA2024031114564633', 'UNDER_REVIEW', 'BS10000001'),
    ('MK00000005', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0255555555', 10, 'IMMA2024031114564633', 'UNDER_REVIEW', 'BS10000002'),
    ('MK00000010', 'Market Name', '서울특별시 종로구', '1111000000', 'Market Content', '0255555555', 10, 'IMMA2024031114564633', 'REGISTERED', 'BS10000002');
;

INSERT INTO tb_quest (quest_id, status, market_id, create_date, update_date)
VALUES
    ('QS00000001', 'UNDER_REVIEW', '`MK00000001`', NOW(), NOW()),
    ('QS00000002', 'UNDER_REVIEW', 'MK00000001', NOW(), NOW());

INSERT INTO tb_reward (reward_id, quest_id, quantity, discount_rate ,content, target, `type`, create_date)
VALUES
    ('RW00000004', 'QS00000001', null, 80, null,'COFFEE', 'DISCOUNT', NOW()),
    ('RW00000005', 'QS00000002', null, 90, null,'COFFEE', 'DISCOUNT', NOW()),
    ('RW00000001', 'QS10000001', null, 90, null,'COFFEE', 'DISCOUNT', NOW()),
    ('RW00000002', 'QS10000002', 1, null, null,'DONUT', 'GIFT', NOW()),
    ('RW00000003', 'QS10000003', null, 20, null,'COFFEE', 'DISCOUNT', NOW());

INSERT INTO tb_mission (mission_id, quest_id, quantity, content, target, `type`, create_date)
VALUES
    ('MS00000004', 'QS00000001', 5, null, 'TEA', 'NORMAL', NOW()),
    ('MS00000005', 'QS00000002', 5, null, 'TEA', 'NORMAL', NOW()),
    ('MS00000001', 'QS10000001', 5, null, 'TEA', 'NORMAL', NOW()),
    ('MS00000002', 'QS10000002', 3, null, 'COFFEE', 'NORMAL', NOW()),
    ('MS00000003', 'QS10000003', 10, '5번 이상 방문', null, 'FREE', NOW());

-- Insert data into tb_image table
INSERT INTO tb_image (file_id, `type`, `size`)
VALUES
    ('IMMA2024031114590814', 'BUSINESS_REGISTRATION_CERTIFICATE', 1024),
    ('IMMA2024031114593704', 'ID_CARD', 2048),
    ('IMMA2024031114564633', 'MARKET_LOGO', 3072),
    ('IMMA2024031115000583', 'RECEIPT', 3072);

-- Insert data into tb_customer table
INSERT INTO `tb_customer` (`customer_id`, `create_date`, `update_date`, `channel`, `email`, `nickname`, `status`, `withdrawn_at`)
VALUES
    ('110804aa-a3f9-4894-93d9-9b446e583b27', '2024-01-30 12:43:05.796058', '2024-01-30 12:43:05.796078', 'APPLE', 'dsss@sample.com', 'USER2024013012430596', 'DORMANT', '2024-01-30'),
    ('4e7b25a2-5c65-4de4-82f7-f0034f5d4615', '2024-02-08 15:10:39.177130', '2024-02-10 23:32:44.243646', 'APPLE', 'dlwlsgml8847@sample.com', 'USER2024020815103834', 'DORMANT', '2024-01-30'),
    ('82eb81c2-7df9-4e47-9362-c71c6ac78f60', '2024-02-14 14:33:10.481530', '2024-02-14 14:33:10.481567', 'APPLE', 'wlsgml8847@sample.com', 'USER2024021414331002', 'DORMANT', '2024-01-30'),
    ('f6744202-f40f-4ce7-b00f-1a8d10456454', '2024-03-05 14:12:32.579302', '2024-03-05 14:12:32.579316', 'KAKAO', 'email@sample.com', 'USER2024030514123269', 'DORMANT', '2024-01-30'),
    ('feeb066f-a118-4dfd-a141-eb8d6f31b8b1', '2024-02-05 13:11:35.501047', '2024-02-05 13:11:35.501083', 'APPLE', '8847wlsgml@naver.com', 'USER2024020513113554', 'DORMANT', '2024-01-30');

-- Insert data into tb_quest table
INSERT INTO tb_quest (quest_id, status, market_id)
VALUES
    ('QS10000001', 'UNDER_REVIEW', 'MK00000001'),
    ('QS10000002', 'PUBLISHED', 'MK00000001'),
    ('QS10000003', 'COMPLETED', 'MK00000003');

-- Insert data into tb_challenge_history table
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, quest_id, receipt_image_id)
VALUES
    ('CH10000001', 'CS10000001', 'UNDER_REVIEW', NULL, 'QS10000001', 'IMMA2024031114590814'),
    ('CH10000002', 'CS10000001', 'UNDER_REVIEW', NULL, 'QS10000002', 'IMMA2024031114593704'),
    ('CH10000003', 'CS10000002', 'APPROVED', NULL, 'QS10000002', 'IMMA2024031114564633'),
    ('CH10000004', 'CS10000003', 'APPROVED', NULL, 'QS10000002', 'IMMA2024031114564633'),
    ('CH10000005', 'CS10000001', 'APPROVED', NULL, 'QS10000002', 'IMMA2024031114593704');

-- Insert data into tb_boss table
INSERT INTO tb_boss (boss_id, status, email)
VALUES
    ('BS10000001', 'ACTIVE', 'user1@example.com'),
    ('BS10000002', 'DORMANT', 'user2@example.com');


INSERT INTO `tb_coupon` (`coupon_id`, `create_date`, `update_date`, `challenge_id`, `expire_date`, `market_id`, `quest_id`, `reward_id`, `status`, `use_date`, `user_id`) VALUES
   ('05ba541a-e1a7-49b2-bc51-441906baf9f2', '2024-03-19 12:07:56.358356', '2024-03-19 12:07:56.358384', '2c54d6b4-4940-410a-95af-1a396b304ea5', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('254fa599-802c-4f9b-a9b6-094a9712ed3b', '2024-03-20 00:26:06.552195', '2024-03-20 00:26:06.552213', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('2d13f281-9ae3-4186-81b1-f9790978e639', '2024-03-19 13:53:28.203559', '2024-03-19 13:53:28.203573', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('78927cc4-c5da-4755-bd0f-674a8f10330f', '2024-03-20 11:19:33.555409', '2024-03-20 11:19:33.555426', '5660fea4-6596-407c-946d-dbc3c926eb56', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('b2d11f2f-9a95-48d5-8175-acf4d9317787', '2024-03-19 13:54:00.292187', '2024-03-19 13:54:00.292205', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('e7824429-3cba-462d-a908-54f5db2b222e', '2024-03-20 00:35:30.949920', '2024-03-20 00:35:30.949947', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454'),
   ('f0dcaf61-1f73-4847-bd21-df0dccb9c55b', '2024-03-19 16:25:19.477983', '2024-03-19 16:25:19.478008', 'b391d3e2-f9fa-4c54-94df-5aebce941d41', '2024-03-30 12:07:56.358356', '7ef3c129-fac7-47a0-a07a-7fd58592cbd6', '832a1c95-c300-471a-919e-0e767978e1e2', '157c4b03-c5b8-4e0b-9618-fa39243abca4', 'ISSUED', NULL, 'f6744202-f40f-4ce7-b00f-1a8d10456454');

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
    ('PR00000003', 'Bob Johnson', '19780930', 'IMMA2024031114593704');

-- Insert data into tb_business_report
INSERT INTO tb_license_business (record_id, license_id, license_image_id, owner_name, market_name, address, postal_code)
VALUES
    ('BR00000001', 'LICENSE123', 'IMMA2024031114590814', 'John Doe', 'Doe Market', '123 Main St', '12345'),
    ('BR00000002', 'LICENSE456', 'IMMA2024031114590814', 'Jane Smith', 'Smith Market', '456 Oak St', '67890'),
    ('BR00000003', 'LICENSE789', 'IMMA2024031114590814', 'Bob Johnson', 'Johnson Market', '789 Elm St', '34567');

-- Insert data into tb_market_auth_history
INSERT INTO tb_market_auth_history (record_id, market_id, review_result, comment, operator_info_id, license_info_id)
VALUES
    ('MA00000001', 'MK00000001', 'APPROVED', 'Good market', 'PR00000001', 'BR00000001'),
    ('MA00000002', 'MK00000002', 'REJECTED', 'Not suitable', 'PR00000002', 'BR00000002'),
    ('MA00000003', 'MK00000003', null, null, 'PR00000003', 'BR00000003');


-- Insert data into tb_notice with different targets
INSERT INTO tb_notice (notice_id, title, content, target)
VALUES
    ('NT00000001', 'Customer Notice', 'Content for customers', 'CUSTOMER'),
    ('NT00000002', 'Boss Notice', 'Content for bosses', 'BOSS'),
    ('NT00000003', 'Admin Notice', 'Content for admins', 'ADMIN'),
    ('NT00000004', 'General Notice', 'Content for everyone', 'ALL'),
    ('NT00000005', 'Another Customer Notice', 'More content for customers', 'CUSTOMER'),
    ('NT00000006', 'Another Admin Notice', 'More content for admins', 'ADMIN');

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
    ('11', 'CS10000007', 'TERMS_OF_SERVICE_CUSTOMER', 1, 'Y',now());

-- Boss 약관 동의 내역 삽입
INSERT INTO tb_agreement_history (agreement_id, user_id, agreement_type, version, accept_yn, create_date)
VALUES
    ('12', 'BS10000001', 'TERMS_OF_SERVICE_BOSS', 1, 'Y', now()),
    ('13', 'BS10000001', 'UNIQUEID_CONSENT_FORM_BOSS', 1, 'N',now()),
    ('14', 'BS10000001', 'PRIVACY_POLICY_BOSS', 1, 'Y',now()),
    ('15', 'BS10000002', 'TERMS_OF_SERVICE_BOSS', 1, 'Y',now()),
    ('16', 'BS10000002', 'UNIQUEID_CONSENT_FORM_BOSS', 1, 'N',now()),
    ('17', 'BS10000002', 'PRIVACY_POLICY_BOSS', 1, 'Y',now());

