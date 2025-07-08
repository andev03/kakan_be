-- data.sql (FIXED - Loại bỏ payment_id khỏi INSERT)
-- Dữ liệu mẫu cho bảng "payment"

-- Giao dịch thành công cho Order ID 101, Account ID 1
INSERT INTO payment (
    order_id,
    account_id,
    amount,
    payment_method,
    payment_date,
    status,
    response_message
) VALUES (
             101, -- order_id từ User Service
             1, -- account_id mẫu
             150000.00,
             'VNPAY',
             '2025-07-08 10:30:00+07',
             'SUCCESS',
             'Payment successful via VNPAY for order 101.'
         );

-- Giao dịch đang chờ xử lý (PENDING) cho Order ID 102, Account ID 2
INSERT INTO payment (
    order_id,
    account_id,
    amount,
    payment_method,
    payment_date,
    status,
    response_message
) VALUES (
             102,
             2,
             250000.00,
             'VNPAY',
             '2025-07-08 10:35:00+07',
             'PENDING',
             'Waiting for VNPAY confirmation for order 102.'
         );

-- Giao dịch thất bại cho Order ID 103, Account ID 1
INSERT INTO payment (
    order_id,
    account_id,
    amount,
    payment_method,
    payment_date,
    status,
    response_message
) VALUES (
             103,
             1,
             100000.00,
             'VNPAY',
             '2025-07-08 10:40:00+07',
             'FAILED',
             'Payment failed: User cancelled the transaction for order 103.'
         );

-- Giao dịch thành công khác cho Order ID 104, Account ID 3
INSERT INTO payment (
    order_id,
    account_id,
    amount,
    payment_method,
    payment_date,
    status,
    response_message
) VALUES (
             104,
             3,
             50000.00,
             'VNPAY',
             '2025-07-08 10:45:00+07',
             'SUCCESS',
             'Premium package activated for order 104.'
         );

-- Giao dịch đang chờ xử lý cho Order ID 105, Account ID 2
INSERT INTO payment (
    order_id,
    account_id,
    amount,
    payment_method,
    payment_date,
    status,
    response_message
) VALUES (
             105,
             2,
             300000.00,
             'VNPAY',
             '2025-07-08 10:50:00+07',
             'PENDING',
             'Awaiting callback from VNPAY for order 105.'
         );
