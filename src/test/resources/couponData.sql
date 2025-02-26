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
    (10, 'User10', 2000, '2025-01-05 12:09:00', '2025-01-05 12:09:00');

INSERT INTO coupon (
    coupon_id,
    coupon_quantity,
    coupon_amount,
    created_at,
    expiration_date,
    max_discount_amount,
    min_usage_amount,
    modify_at,
    coupon_name,
    coupon_type
) VALUES
      (1, 5, 5000, '2025-01-01 12:00:00', '2025-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'New Year Sale', 'AMOUNT'),
      (2, 15, 5000, '2025-01-01 12:00:00', '2025-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'New Year Sale', 'AMOUNT'),
      (3, 25, 5000, '2025-01-01 12:00:00', '2025-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'New Year Sale', 'AMOUNT');


