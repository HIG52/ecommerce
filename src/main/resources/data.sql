/*유저(잔액)*/
INSERT INTO user (user_id, user_name, balance, created_at, modify_at) VALUES
    (1, 'User1', 1000, '2025-01-05 12:00:00', '2025-01-05 12:00:00');

/*정상쿠폰*/
INSERT INTO coupon (coupon_id, coupon_quantity, coupon_amount, created_at, expiration_date, max_discount_amount, min_usage_amount, modify_at, coupon_name, coupon_type) VALUES
    (1, 30, 5000, '2025-01-01 12:00:00', '2025-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'New Year Sale', 'AMOUNT');

/*만료쿠폰*/
INSERT INTO coupon (coupon_id, coupon_quantity, coupon_amount, created_at, expiration_date, max_discount_amount, min_usage_amount, modify_at, coupon_name, coupon_type) VALUES
    (2, 30, 5000, '2021-01-01 12:00:00', '2022-12-31 23:59:59', 20000, 10000, '2025-01-01 12:00:00', 'expired sail', 'AMOUNT');

/*상품*/
INSERT INTO product (product_quantity, created_at, modify_at, product_id, product_price, product_name)
VALUES
    (100, NOW() - INTERVAL 3 DAY, NOW(), 101, 500, 'Product 101'),
    (150, NOW() - INTERVAL 3 DAY, NOW(), 102, 600, 'Product 102'),
    (200, NOW() - INTERVAL 3 DAY, NOW(), 103, 700, 'Product 103'),
    (250, NOW() - INTERVAL 3 DAY, NOW(), 104, 800, 'Product 104'),
    (300, NOW() - INTERVAL 3 DAY, NOW(), 105, 900, 'Product 105'),

    (350, NOW() - INTERVAL 2 DAY, NOW(), 106, 1000, 'Product 106'),
    (400, NOW() - INTERVAL 2 DAY, NOW(), 107, 1100, 'Product 107'),
    (450, NOW() - INTERVAL 2 DAY, NOW(), 108, 1200, 'Product 108'),
    (500, NOW() - INTERVAL 2 DAY, NOW(), 109, 1300, 'Product 109'),
    (550, NOW() - INTERVAL 2 DAY, NOW(), 110, 1400, 'Product 110'),

    (600, NOW() - INTERVAL 1 DAY, NOW(), 111, 1500, 'Product 111'),
    (650, NOW() - INTERVAL 1 DAY, NOW(), 112, 1600, 'Product 112'),
    (700, NOW() - INTERVAL 1 DAY, NOW(), 113, 1700, 'Product 113'),
    (750, NOW() - INTERVAL 1 DAY, NOW(), 114, 1800, 'Product 114'),
    (800, NOW() - INTERVAL 1 DAY, NOW(), 115, 1900, 'Product 115'),

    (850, NOW() - INTERVAL 1 DAY, NOW(), 116, 2000, 'Product 116'),
    (900, NOW() - INTERVAL 2 DAY, NOW(), 117, 2100, 'Product 117'),
    (950, NOW() - INTERVAL 3 DAY, NOW(), 118, 2200, 'Product 118'),
    (1000, NOW() - INTERVAL 1 DAY, NOW(), 119, 2300, 'Product 119'),
    (1050, NOW() - INTERVAL 2 DAY, NOW(), 120, 2400, 'Product 120');