CREATE TABLE `user` (
                        `user_id` bigint PRIMARY KEY,
                        `user_name` varchar(255),
                        `balance` bigint,
                        `created_at` timestamp,
                        `modify_at` timestamp
);

CREATE TABLE `user_balance_history` (
                                        `user_balance_history_id` bigint PRIMARY KEY,
                                        `user_id` bigint,
                                        `history_type` varchar(255),
                                        `amount` bigint,
                                        `inserted_at` datetime,
                                        `created_at` timestamp,
                                        `modify_at` timestamp
);

CREATE TABLE `coupon` (
                          `coupon_id` bigint PRIMARY KEY,
                          `coupon_name` varchar(255),
                          `coupon_type` varchar(255),
                          `coupon_amount` bigint,
                          `coupon_quantity` int,
                          `expiration_date` datetime,
                          `min_usage_amount` bigint,
                          `max_discount_amount` bigint,
                          `created_at` timestamp,
                          `modify_at` timestamp
);

CREATE TABLE `user_coupon` (
                               `user_coupon` bigint PRIMARY KEY,
                               `coupon_id` bigint,
                               `user_id` bigint,
                               `created_at` timestamp,
                               `modify_at` timestamp
);

CREATE TABLE `product` (
                           `product_id` bigint PRIMARY KEY,
                           `product_name` varchar(255),
                           `product_price` bigint,
                           `product_quantity` int,
                           `created_at` timestamp,
                           `modify_at` timestamp
);

CREATE TABLE `order` (
                         `order_id` bigint PRIMARY KEY,
                         `user_id` bigint,
                         `order_total_amount` bigint,
                         `payment_status` varchar(255),
                         `status` varchar(255),
                         `created_at` timestamp,
                         `modify_at` timestamp
);

CREATE TABLE `order_detail` (
                                `order_detail_id` bigint PRIMARY KEY,
                                `order_id` bigint,
                                `product_id` bigint,
                                `order_quantity` int,
                                `order_amount` bigint,
                                `created_at` timestamp,
                                `modify_at` timestamp
);

CREATE TABLE `payment` (
                           `payment_id` bigint PRIMARY KEY,
                           `order_id` bigint,
                           `payment_amount` bigint,
                           `payment_status` varchar(255),
                           `created_at` timestamp,
                           `modify_at` timestamp
);