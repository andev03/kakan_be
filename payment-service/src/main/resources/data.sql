INSERT INTO payment_methods (id, user_id, method_type, method_token, last_used)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', -- user_id
    'VNPAY',
    'tok_1234567890',
    NOW()
);

INSERT INTO payments (id, order_id, payment_method_id, provider_txn_id, amount, status, paid_at)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    '11111111-1111-1111-1111-111111111111',
    'VNPAY_TXN_001',
    50000.00,
    'SUCCESS',
    NOW()
);
