TRUNCATE TABLE user;
TRUNCATE TABLE `order`;
INSERT INTO user (user_id, user_name, balance, created_at, modify_at) VALUES
    (1, 'User1', 15000, '2025-01-05 12:00:00', '2025-01-05 12:00:00');

INSERT INTO `order` (order_id, created_at, modify_at, order_total_amount, user_id, payment_status, status) VALUES
   (1, '2025-01-01 10:00:00', '2025-01-01 10:00:00', 5000, 1, 'PENDING', 'ORDERED');
