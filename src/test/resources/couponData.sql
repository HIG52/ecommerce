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
      (1, 30, 5000, '2025-01-01 12:00:00', '2025-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'New Year Sale', 'AMOUNT'),
      (2, 50, 10, '2025-01-01 12:30:00', '2025-06-30 23:59:59', 5000, 500, '2025-01-01 12:30:00', 'Summer Discount', 'PERCENTAGE'),
      (3, 200, 1500, '2025-01-02 14:00:00', '2025-03-31 23:59:59', 5000, 1000, '2025-01-02 14:00:00', 'Spring Offer', 'AMOUNT'),
      (4, 300, 5, '2025-01-03 09:00:00', '2025-12-31 23:59:59', 3000, 500, '2025-01-03 09:00:00', 'End of Year Sale', 'PERCENTAGE'),
      (5, 150, 2000, '2025-01-04 10:00:00', '2025-09-30 23:59:59', 10000, 5000, '2025-01-04 10:00:00', 'Exclusive Deal', 'AMOUNT'),
      (6, 80, 7, '2025-01-05 15:00:00', '2025-07-15 23:59:59', 3500, 700, '2025-01-05 15:00:00', 'Flash Sale', 'PERCENTAGE'),
      (7, 60, 1000, '2025-01-06 13:00:00', '2025-04-30 23:59:59', 4000, 2000, '2025-01-06 13:00:00', 'Spring Clearance', 'AMOUNT'),
      (8, 120, 5, '2025-01-07 11:00:00', '2025-05-31 23:59:59', 3000, 600, '2025-01-07 11:00:00', 'Weekly Promo', 'PERCENTAGE'),
      (9, 200, 500, '2025-01-08 14:00:00', '2025-08-31 23:59:59', 2000, 1000, '2025-01-08 14:00:00', 'August Deal', 'AMOUNT'),
      (10, 100, 20, '2025-01-09 10:00:00', '2025-10-31 23:59:59', 1500, 300, '2025-01-09 10:00:00', 'Halloween Sale', 'PERCENTAGE');
