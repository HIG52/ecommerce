TRUNCATE TABLE user;
TRUNCATE TABLE coupon;
TRUNCATE TABLE product;
TRUNCATE TABLE order_detail;
TRUNCATE TABLE `order`;
TRUNCATE TABLE payment;
TRUNCATE TABLE user_balance_history;
TRUNCATE TABLE user_coupon;

-- 최근 3일의 날짜 생성
INSERT INTO order_detail (order_quantity, created_at, modify_at, order_amount, order_detail_id, order_id, product_id)
VALUES
-- 첫째 날 데이터
(20, NOW() - INTERVAL 3 DAY, NOW(), 5000, 1, 1, 101),
(19, NOW() - INTERVAL 3 DAY, NOW(), 3000, 2, 1, 102),
(18, NOW() - INTERVAL 3 DAY, NOW(), 7000, 3, 2, 103),
(17, NOW() - INTERVAL 3 DAY, NOW(), 2000, 4, 2, 104),
(16, NOW() - INTERVAL 3 DAY, NOW(), 4000, 5, 3, 105),

-- 둘째 날 데이터
(6, NOW() - INTERVAL 2 DAY, NOW(), 6000, 6, 4, 106),
(8, NOW() - INTERVAL 2 DAY, NOW(), 8000, 7, 4, 107),
(10, NOW() - INTERVAL 2 DAY, NOW(), 10000, 8, 5, 108),
(9, NOW() - INTERVAL 2 DAY, NOW(), 9000, 9, 5, 109),
(3, NOW() - INTERVAL 2 DAY, NOW(), 3000, 10, 6, 110),

-- 셋째 날 데이터
(1, NOW() - INTERVAL 1 DAY, NOW(), 1000, 11, 6, 111),
(4, NOW() - INTERVAL 1 DAY, NOW(), 4000, 12, 7, 112),
(5, NOW() - INTERVAL 1 DAY, NOW(), 5000, 13, 7, 113),
(6, NOW() - INTERVAL 1 DAY, NOW(), 6000, 14, 8, 114),
(7, NOW() - INTERVAL 1 DAY, NOW(), 8000, 15, 8, 115),

-- 랜덤 데이터
(2, NOW() - INTERVAL 1 DAY, NOW(), 2000, 16, 9, 116),
(7, NOW() - INTERVAL 2 DAY, NOW(), 7000, 17, 9, 117),
(3, NOW() - INTERVAL 3 DAY, NOW(), 3000, 18, 10, 118),
(5, NOW() - INTERVAL 1 DAY, NOW(), 5000, 19, 10, 119),
(6, NOW() - INTERVAL 2 DAY, NOW(), 6000, 20, 10, 120);

-- Product 테이블 데이터 생성
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