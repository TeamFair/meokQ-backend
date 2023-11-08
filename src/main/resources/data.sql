INSERT INTO tb_market (market_id, `name`, address, district, content, phone, ticket_count, logo_image, status, president_id, create_date, update_date)
VALUES ('MK00000001', 'Market Name', 'Market Address', 'Market District', 'Market Content', 'Market Phone', 0, 'Logo Image URL', 'UNDER_REVIEW', 'President ID', NOW(), NOW());
;

INSERT INTO tb_quest (quest_id, quest_status, market_id, create_date, update_date)
VALUES
    ('QS00000000', 'UNDER_REVIEW', 'MK00000001', NOW(), NOW()),
    ('QS00000001', 'UNDER_REVIEW', 'MK00000001', NOW(), NOW()),
    ('QS00000002', 'UNDER_REVIEW', 'MK00000001', NOW(), NOW());

INSERT INTO tb_reward (reward_id, quest_id, quantity, content, target, `type`, create_date)
VALUES
    ('RW00000000', 'QS00000000', 10, 'Reward 1 Content', 'Target 1', 'DISCOUNT', NOW()),
    ('RW00000001', 'QS00000001', 15, 'Reward 2 Content', 'Target 2', 'GIFT', NOW()),
    ('RW00000002', 'QS00000002', 20, 'Reward 3 Content', 'Target 3', 'DISCOUNT', NOW());

INSERT INTO tb_mission (mission_id, quest_id, quantity, content, target, `type`, create_date)
VALUES
    ('MS00000000', 'QS00000000', 5, 'Mission 1 Content', 'Target 1', 'FREE', NOW()),
    ('MS00000001', 'QS00000001', 3, 'Mission 2 Content', 'Target 2', 'NORMAL', NOW()),
    ('MS00000002', 'QS00000002', 10, 'Mission 3 Content', 'Target 3', 'FREE', NOW());

-- 퀘스트 'QS00000000'의 도전 내역
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, create_date, quest_id, receipt_image)
VALUES
    ('CH00000000', 'Customer1', 'UNDER_REVIEW', NULL, NOW(), 'QS00000000', 'Image1'),
    ('CH00000001', 'Customer2', 'UNDER_REVIEW', NULL, NOW(), 'QS00000000', 'Image2'),
    ('CH00000002', 'Customer3', 'UNDER_REVIEW', NULL, NOW(), 'QS00000000', 'Image3');

-- 퀘스트 'QS00000001'의 도전 내역
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, create_date, quest_id, receipt_image)
VALUES
    ('CH00000003', 'Customer1', 'UNDER_REVIEW', NULL, NOW(), 'QS00000001', 'Image4'),
    ('CH00000004', 'Customer2', 'UNDER_REVIEW', NULL, NOW(), 'QS00000001', 'Image5'),
    ('CH00000005', 'Customer3', 'UNDER_REVIEW', NULL, NOW(), 'QS00000001', 'Image6');

-- 퀘스트 'QS00000002'의 도전 내역
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, create_date, quest_id, receipt_image)
VALUES
    ('CH00000006', 'Customer1', 'UNDER_REVIEW', NULL, NOW(), 'QS00000002', 'Image7'),
    ('CH00000007', 'Customer2', 'UNDER_REVIEW', NULL, NOW(), 'QS00000002', 'Image8'),
    ('CH00000008', 'Customer3', 'UNDER_REVIEW', NULL, NOW(), 'QS00000002', 'Image9');

-- 퀘스트 'QS00000003'의 도전 내역
INSERT INTO tb_challenge_history (challenge_id, customer_id, status, reject_reason, create_date, quest_id, receipt_image)
VALUES
    ('CH00000009', 'Customer1', 'UNDER_REVIEW', NULL, NOW(), 'QS00000003', 'Image10'),
    ('CH00000010', 'Customer2', 'UNDER_REVIEW', NULL, NOW(), 'QS00000003', 'Image11'),
    ('CH00000011', 'Customer3', 'UNDER_REVIEW', NULL, NOW(), 'QS00000003', 'Image12');
