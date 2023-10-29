INSERT INTO tb_admin (delete_yn, status, create_date, update_date, admin_id, name, nickname)
VALUES
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin1', 'Admin Name 1', 'Nickname 1'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin2', 'Admin Name 2', 'Nickname 2'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin3', 'Admin Name 3', 'Nickname 3'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin4', 'Admin Name 4', 'Nickname 4'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin5', 'Admin Name 5', 'Nickname 5'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin6', 'Admin Name 6', 'Nickname 6'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin7', 'Admin Name 7', 'Nickname 7'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin8', 'Admin Name 8', 'Nickname 8'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin9', 'Admin Name 9', 'Nickname 9'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin10', 'Admin Name 10', 'Nickname 10'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin11', 'Admin Name 11', 'Nickname 11'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin12', 'Admin Name 12', 'Nickname 12'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin13', 'Admin Name 13', 'Nickname 13'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin14', 'Admin Name 14', 'Nickname 14'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin15', 'Admin Name 15', 'Nickname 15'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin16', 'Admin Name 16', 'Nickname 16'),
    (0, 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin17', 'Admin Name 17', 'Nickname 17'),
    (0, 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin18', 'Admin Name 18', 'Nickname 18'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin19', 'Admin Name 19', 'Nickname 19'),
    (0, 0, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'admin20', 'Admin Name 20', 'Nickname 20');

INSERT INTO tb_business_hour (close_time, day_week, delete_yn, open_time, sequence, create_date, update_date, market_id)
VALUES
    ('12:00:00', 0, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market1'),
    ('18:00:00', 0, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market1'),
    ('12:00:00', 1, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market2'),
    ('18:00:00', 1, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market2'),
    ('12:00:00', 2, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market3'),
    ('18:00:00', 2, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market3'),
    ('12:00:00', 3, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market4'),
    ('18:00:00', 3, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market4'),
    ('12:00:00', 4, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market5'),
    ('18:00:00', 4, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market5'),
    ('12:00:00', 5, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market6'),
    ('18:00:00', 5, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market6'),
    ('12:00:00', 6, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market7'),
    ('18:00:00', 6, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market7'),
    ('12:00:00', 0, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market8'),
    ('18:00:00', 0, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market8'),
    ('12:00:00', 1, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market9'),
    ('18:00:00', 1, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market9'),
    ('12:00:00', 2, 0, '08:00:00', 1, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market10'),
    ('18:00:00', 2, 0, '12:00:00', 2, '2023-10-28 12:00:00', '2023-10-28 12:00:00', 'market10');

INSERT INTO tb_challenge_history (delete_yn, quest_id, sequence, status, create_date, update_date, customer_id)
VALUES
    (0, 1, 1, 0, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'customer1'),
    (0, 2, 1, 1, '2023-10-16 09:30:00', '2023-11-02 15:45:00', 'customer2'),
    (0, 2, 2, 0, '2023-10-17 14:15:00', '2023-11-03 11:20:00', 'customer2'),
    (0, 3, 1, 2, '2023-10-18 08:45:00', '2023-11-04 12:00:00', 'customer3'),
    (0, 3, 2, 1, '2023-10-19 12:30:00', '2023-11-05 13:10:00', 'customer3'),
    (0, 4, 1, 0, '2023-10-20 07:15:00', '2023-11-06 16:20:00', 'customer4'),
    (0, 5, 1, 2, '2023-10-21 11:50:00', '2023-11-07 14:35:00', 'customer5'),
    (0, 5, 2, 0, '2023-10-22 13:25:00', '2023-11-08 10:40:00', 'customer5'),
    (0, 6, 1, 1, '2023-10-23 15:05:00', '2023-11-09 12:50:00', 'customer6'),
    (0, 6, 2, 2, '2023-10-24 09:40:00', '2023-11-10 14:30:00', 'customer6'),
    (0, 7, 1, 0, '2023-10-25 16:00:00', '2023-11-11 07:55:00', 'customer7'),
    (0, 8, 1, 2, '2023-10-26 10:15:00', '2023-11-12 16:05:00', 'customer8'),
    (0, 8, 2, 1, '2023-10-27 14:45:00', '2023-11-13 09:15:00', 'customer8'),
    (0, 9, 1, 0, '2023-10-28 08:20:00', '2023-11-14 17:20:00', 'customer9'),
    (0, 9, 2, 2, '2023-10-29 16:30:00', '2023-11-15 11:30:00', 'customer9'),
    (0, 10, 1, 1, '2023-10-30 12:40:00', '2023-11-16 14:45:00', 'customer10'),
    (0, 10, 2, 0, '2023-10-31 15:50:00', '2023-11-17 08:55:00', 'customer10');

INSERT INTO tb_coupon (reward_sequence, status, expiry_date, use_date, coupon_id, customer_id, quest_id)
VALUES
    (1, 0, '2023-11-01 10:00:00', NULL, 'coupon1', 'customer1', 'quest1'),
    (2, 1, '2023-12-15 15:30:00', '2023-12-01 14:45:00', 'coupon2', 'customer1', 'quest2'),
    (3, 0, '2023-11-15 08:30:00', NULL, 'coupon3', 'customer2', 'quest3'),
    (4, 1, '2023-12-31 23:59:59', '2023-12-10 18:20:00', 'coupon4', 'customer2', 'quest4'),
    (5, 0, '2023-11-30 16:45:00', NULL, 'coupon5', 'customer3', 'quest5'),
    (6, 1, '2024-01-10 11:00:00', '2023-12-05 17:15:00', 'coupon6', 'customer3', 'quest6'),
    (7, 0, '2023-12-10 19:30:00', NULL, 'coupon7', 'customer4', 'quest7'),
    (8, 1, '2024-01-15 22:00:00', '2023-12-15 08:30:00', 'coupon8', 'customer4', 'quest8'),
    (9, 0, '2023-12-20 10:15:00', NULL, 'coupon9', 'customer5', 'quest9'),
    (10, 1, '2024-01-20 16:30:00', '2023-12-20 12:45:00', 'coupon10', 'customer5', 'quest10');

INSERT INTO tb_customer (delete_yn, status, create_date, update_date, customer_id, name, nickname)
VALUES
    (0, 0, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'customer1', 'John Doe', 'johnny1'),
    (0, 1, '2023-10-16 11:30:00', '2023-11-02 15:45:00', 'customer2', 'Jane Smith', 'jane2'),
    (0, 0, '2023-10-17 09:15:00', '2023-11-03 12:20:00', 'customer3', 'Michael Johnson', 'mike3'),
    (1, 0, '2023-10-18 14:30:00', '2023-11-04 16:50:00', 'customer4', 'Emily Brown', 'emily4'),
    (0, 1, '2023-10-19 16:00:00', '2023-11-05 10:15:00', 'customer5', 'William Lee', 'will5'),
    (0, 0, '2023-10-20 08:45:00', '2023-11-06 09:30:00', 'customer6', 'Olivia Martinez', 'olivia6'),
    (1, 0, '2023-10-21 13:25:00', '2023-11-07 11:00:00', 'customer7', 'James Anderson', 'james7'),
    (0, 1, '2023-10-22 12:15:00', '2023-11-08 14:45:00', 'customer8', 'Sophia Wilson', 'sophia8'),
    (0, 0, '2023-10-23 10:40:00', '2023-11-09 12:55:00', 'customer9', 'Daniel Harris', 'daniel9'),
    (1, 1, '2023-10-24 17:20:00', '2023-11-10 15:30:00', 'customer10', 'Mia Clark', 'mia10');

/*INSERT INTO tb_image (delete_yn, type, create_date, update_date, image_id, image_url, notice_id)
VALUES
    (0, 0, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'image1', 'https://example.com/image1.jpg', 'notice1'),
    (0, 0, '2023-10-16 11:30:00', '2023-11-02 15:45:00', 'image2', 'https://example.com/image2.jpg', 'notice2'),
    (0, 0, '2023-10-17 09:15:00', '2023-11-03 12:20:00', 'image3', 'https://example.com/image3.jpg', 'notice3'),
    (1, 0, '2023-10-18 14:30:00', '2023-11-04 16:50:00', 'image4', 'https://example.com/image4.jpg', 'notice4'),
    (0, 0, '2023-10-19 16:00:00', '2023-11-05 10:15:00', 'image5', 'https://example.com/image5.jpg', 'notice5'),
    (0, 0, '2023-10-20 08:45:00', '2023-11-06 09:30:00', 'image6', 'https://example.com/image6.jpg', 'notice6'),
    (1, 0, '2023-10-21 13:25:00', '2023-11-07 11:00:00', 'image7', 'https://example.com/image7.jpg', 'notice7'),
    (0, 0, '2023-10-22 12:15:00', '2023-11-08 14:45:00', 'image8', 'https://example.com/image8.jpg', 'notice8'),
    (0, 0, '2023-10-23 10:40:00', '2023-11-09 12:55:00', 'image9', 'https://example.com/image9.jpg', 'notice9'),
    (1, 0, '2023-10-24 17:20:00', '2023-11-10 15:30:00', 'image10', 'https://example.com/image10.jpg', 'notice10')
;*/

INSERT INTO tb_management (delete_yn, create_date, update_date, manager_id, market_id)
VALUES
    (0, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'manager1', 'market1'),
    (0, '2023-10-16 11:30:00', '2023-11-02 15:45:00', 'manager2', 'market2'),
    (0, '2023-10-17 09:15:00', '2023-11-03 12:20:00', 'manager3', 'market3'),
    (1, '2023-10-18 14:30:00', '2023-11-04 16:50:00', 'manager4', 'market4'),
    (0, '2023-10-19 16:00:00', '2023-11-05 10:15:00', 'manager5', 'market5'),
    (0, '2023-10-20 08:45:00', '2023-11-06 09:30:00', 'manager6', 'market6'),
    (1, '2023-10-21 13:25:00', '2023-11-07 11:00:00', 'manager7', 'market7'),
    (0, '2023-10-22 12:15:00', '2023-11-08 14:45:00', 'manager8', 'market8'),
    (0, '2023-10-23 10:40:00', '2023-11-09 12:55:00', 'manager9', 'market9'),
    (1, '2023-10-24 17:20:00', '2023-11-10 15:30:00', 'manager10', 'market10');

INSERT INTO tb_market (delete_yn, ticket_count, create_date, update_date, address, content, district, market_id, name, phone, president_id)
VALUES
    (0, 100, '2023-10-15 10:00:00', '2023-11-01 14:30:00', '123 Main St', 'A large market with many vendors.', '0000000000', 'market1', 'Downtown Market', '555-123-4567', 'manager1'),
    (0, 150, '2023-10-16 11:30:00', '2023-11-02 15:45:00', '456 Elm St', 'A cozy market in the suburbs.', '0000000000', 'market2', 'Suburbia Market', '555-234-5678', 'manager2'),
    (0, 80, '2023-10-17 09:15:00', '2023-11-03 12:20:00', '789 Oak St', 'A small market in a rural area.', '0000000000', 'market3', 'Ruralville Market', '555-345-6789', 'manager3'),
    (1, 120, '2023-10-18 14:30:00', '2023-11-04 16:50:00', '101 Pine St', 'An upscale market in the city.', '0000000000', 'market4', 'City Center Market', '555-456-7890', 'manager4'),
    (0, 95, '2023-10-19 16:00:00', '2023-11-05 10:15:00', '246 Cedar St', 'An eclectic market with unique items.', 'Artsyville', 'market5', 'Artsyville Market', '555-567-8901', 'manager5'),
    (0, 110, '2023-10-20 08:45:00', '2023-11-06 09:30:00', '303 Birch St', 'A family-friendly market with activities.', 'Familyville', 'market6', 'Familyville Market', '555-678-9012', 'manager6'),
    (1, 75, '2023-10-21 13:25:00', '2023-11-07 11:00:00', '404 Redwood St', 'An organic market with fresh produce.', 'Greenville', 'market7', 'Greenville Market', '555-789-0123', 'manager7'),
    (0, 135, '2023-10-22 12:15:00', '2023-11-08 14:45:00', '505 Maple St', 'A market known for handmade crafts.', 'Craftsville', 'market8', 'Craftsville Market', '555-890-1234', 'manager8'),
    (0, 70, '2023-10-23 10:40:00', '2023-11-09 12:55:00', '606 Oakwood St', 'A market focused on vintage goods.', 'Retroville', 'market9', 'Retroville Market', '555-901-2345', 'manager9'),
    (1, 125, '2023-10-24 17:20:00', '2023-11-10 15:30:00', '707 Cherry St', 'An international market with diverse offerings.', 'Multiville', 'market10', 'Multiville Market', '555-012-3456', 'manager10');

INSERT INTO tb_market_manager (delete_yn, status, create_date, update_date, manager_id, name, nickname, position)
VALUES
    (0, 1, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'manager1', 'John Doe', 'John', 'Manager'),
    (0, 2, '2023-10-16 11:30:00', '2023-11-02 15:45:00', 'manager2', 'Jane Smith', 'Jane', 'Supervisor'),
    (0, 0, '2023-10-17 09:15:00', '2023-11-03 12:20:00', 'manager3', 'Bob Johnson', 'Bob', 'Staff'),
    (1, 1, '2023-10-18 14:30:00', '2023-11-04 16:50:00', 'manager4', 'Alice Brown', 'Alice', 'Manager'),
    (0, 2, '2023-10-19 16:00:00', '2023-11-05 10:15:00', 'manager5', 'Chris Wilson', 'Chris', 'Supervisor'),
    (0, 0, '2023-10-20 08:45:00', '2023-11-06 09:30:00', 'manager6', 'Ella Davis', 'Ella', 'Staff'),
    (1, 1, '2023-10-21 13:25:00', '2023-11-07 11:00:00', 'manager7', 'David Lee', 'David', 'Manager'),
    (0, 2, '2023-10-22 12:15:00', '2023-11-08 14:45:00', 'manager8', 'Grace Hall', 'Grace', 'Supervisor'),
    (0, 0, '2023-10-23 10:40:00', '2023-11-09 12:55:00', 'manager9', 'Frank Turner', 'Frank', 'Staff'),
    (1, 1, '2023-10-24 17:20:00', '2023-11-10 15:30:00', 'manager10', 'Helen Scott', 'Helen', 'Manager');

INSERT INTO tb_mission (count, delete_yn, sequence, create_date, update_date, content, quest_id, target)
VALUES
    (10, 0, 1, '2023-10-15 10:00:00', '2023-11-01 14:30:00', 'Mission 1', 'quest1', 'Target 1'),
    (5, 0, 2, '2023-10-16 11:30:00', '2023-11-02 15:45:00', 'Mission 2', 'quest1', 'Target 2'),
    (8, 0, 3, '2023-10-17 09:15:00', '2023-11-03 12:20:00', 'Mission 3', 'quest2', 'Target 3'),
    (15, 0, 4, '2023-10-18 14:30:00', '2023-11-04 16:50:00', 'Mission 4', 'quest2', 'Target 4'),
    (12, 0, 5, '2023-10-19 16:00:00', '2023-11-05 10:15:00', 'Mission 5', 'quest3', 'Target 5'),
    (3, 0, 6, '2023-10-20 08:45:00', '2023-11-06 09:30:00', 'Mission 6', 'quest3', 'Target 6'),
    (7, 0, 7, '2023-10-21 13:25:00', '2023-11-07 11:00:00', 'Mission 7', 'quest4', 'Target 7'),
    (9, 0, 8, '2023-10-22 12:15:00', '2023-11-08 14:45:00', 'Mission 8', 'quest4', 'Target 8'),
    (6, 0, 9, '2023-10-23 10:40:00', '2023-11-09 12:55:00', 'Mission 9', 'quest5', 'Target 9'),
    (20, 0, 10, '2023-10-24 17:20:00', '2023-11-10 15:30:00', 'Mission 10', 'quest5', 'Target 10');

INSERT INTO tb_quest (delete_yn, quest_status, create_date, update_date, market_id, quest_id)
VALUES
    (0, 0, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market1', 'quest1'),
    (0, 1, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market2', 'quest2'),
    (0, 2, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market3', 'quest3'),
    (0, 0, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market4', 'quest4'),
    (0, 1, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market5', 'quest5'),
    (0, 2, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market6', 'quest6'),
    (0, 0, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market7', 'quest7'),
    (0, 1, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market8', 'quest8'),
    (0, 2, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market9', 'quest9'),
    (0, 0, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'market10', 'quest10');

INSERT INTO tb_reward (count, delete_yn, sequence, create_date, update_date, content, discount_rate, quest_id, target)
VALUES
    (10, 0, 1, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 1', '5%', 'quest1', 'target1'),
    (8, 0, 2, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 2', '10%', 'quest2', 'target2'),
    (12, 0, 3, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 3', '15%', 'quest3', 'target3'),
    (15, 0, 4, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 4', '8%', 'quest4', 'target4'),
    (9, 0, 5, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 5', '12%', 'quest5', 'target5'),
    (11, 0, 6, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 6', '7%', 'quest6', 'target6'),
    (14, 0, 7, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 7', '9%', 'quest7', 'target7'),
    (13, 0, 8, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 8', '11%', 'quest8', 'target8'),
    (7, 0, 9, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 9', '6%', 'quest9', 'target9'),
    (10, 0, 10, '2023-10-28 00:00:00', '2023-10-28 00:00:00', 'Reward 10', '13%', 'quest10', 'target10');
