TRUNCATE TABLE user;
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
    (10, 'User10', 30000000, '2025-01-05 12:09:00', '2025-01-05 12:09:00');

INSERT INTO product (product_id, product_name, product_price, product_quantity, created_at, modify_at) VALUES
    (1, 'Laptop', 1200000, 10, '2025-01-01 12:00:00', '2025-01-05 15:00:00'),
    (2, 'Smartphone', 800000, 20, '2025-01-02 10:00:00', '2025-01-06 16:00:00');