/*TRUNCATE TABLE user;
TRUNCATE TABLE coupon;
TRUNCATE TABLE product;
TRUNCATE TABLE order_detail;
TRUNCATE TABLE `order`;
TRUNCATE TABLE payment;
TRUNCATE TABLE user_balance_history;
TRUNCATE TABLE user_coupon;

INSERT INTO user (user_id, user_name, balance, created_at, modify_at) VALUES
    (1, 'User1', 1000, '2025-01-05 12:00:00', '2025-01-05 12:00:00'),
    (2, 'User2', 2000, '2025-01-05 12:01:00', '2025-01-05 12:01:00'),
    (3, 'User3', 2000, '2025-01-05 12:02:00', '2025-01-05 12:02:00'),
    (4, 'User4', 2000, '2025-01-05 12:03:00', '2025-01-05 12:03:00'),
    (5, 'User5', 2000, '2025-01-05 12:04:00', '2025-01-05 12:04:00'),
    (6, 'User6', 2000, '2025-01-05 12:05:00', '2025-01-05 12:05:00'),
    (7, 'User7', 2000, '2025-01-05 12:06:00', '2025-01-05 12:06:00'),
    (8, 'User8', 2000, '2025-01-05 12:07:00', '2025-01-05 12:07:00'),
    (9, 'User9', 2000, '2025-01-05 12:08:00', '2025-01-05 12:08:00'),
    (10, 'User10', 2000, '2025-01-05 12:09:00', '2025-01-05 12:09:00');
*/

TRUNCATE TABLE user;
TRUNCATE TABLE coupon;
TRUNCATE TABLE product;
TRUNCATE TABLE order_detail;
TRUNCATE TABLE `order`;
TRUNCATE TABLE payment;
TRUNCATE TABLE user_balance_history;
TRUNCATE TABLE user_coupon;

INSERT INTO user (user_id, user_name, balance, created_at, modify_at, version) VALUES
                                                                                   (1, 'User1', 1000, '2025-01-05 12:00:00', '2025-01-05 12:00:00', 0),
                                                                                   (2, 'User2', 2000, '2025-01-05 12:01:00', '2025-01-05 12:01:00', 0),
                                                                                   (3, 'User3', 2000, '2025-01-05 12:02:00', '2025-01-05 12:02:00', 0),
                                                                                   (4, 'User4', 2000, '2025-01-05 12:03:00', '2025-01-05 12:03:00', 0),
                                                                                   (5, 'User5', 2000, '2025-01-05 12:04:00', '2025-01-05 12:04:00', 0),
                                                                                   (6, 'User6', 2000, '2025-01-05 12:05:00', '2025-01-05 12:05:00', 0),
                                                                                   (7, 'User7', 2000, '2025-01-05 12:06:00', '2025-01-05 12:06:00', 0),
                                                                                   (8, 'User8', 2000, '2025-01-05 12:07:00', '2025-01-05 12:07:00', 0),
                                                                                   (9, 'User9', 2000, '2025-01-05 12:08:00', '2025-01-05 12:08:00', 0),
                                                                                   (10, 'User10', 2000, '2025-01-05 12:09:00', '2025-01-05 12:09:00', 0);
