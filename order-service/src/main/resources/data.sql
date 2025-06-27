INSERT INTO orders (id, user_id, total_amount, status)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    250000.00,
    'PENDING'
);

INSERT INTO order_items (id, order_id, product_id, quantity, price)
VALUES
(
    '22222222-2222-2222-2222-222222222222',
    '11111111-1111-1111-1111-111111111111',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    2,
    50000.00
),
(
    '33333333-3333-3333-3333-333333333333',
    '11111111-1111-1111-1111-111111111111',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    1,
    150000.00
);
INSERT INTO order_status_logs (id, order_id, status, note)
VALUES
(
    '44444444-4444-4444-4444-444444444444',
    '11111111-1111-1111-1111-111111111111',
    'PENDING',
    'Đơn hàng được tạo'
),
(
    '55555555-5555-5555-5555-555555555555',
    '11111111-1111-1111-1111-111111111111',
    'CONFIRMED',
    'Đã xác nhận đơn hàng'
);
