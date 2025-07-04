-- Dữ liệu mẫu

-- account
--password alice: password: hashed_pwd_1
--password bob: password: hashed_pwd_2
--password nhatan: password: hashed_pwd_3
--password admin: password: hashed_pwd_4

INSERT INTO account (user_name, email, password, is_active, role)
VALUES ('alice', 'alice@example.com', '$2a$12$FsEQT3Zw0PeNv7iW5jmsF.6BZ70WKzIkdpqThESAnE.G2FhgvVoy6', TRUE, 'STUDENT'),
       ('Bob', 'bob@example.com', '$2a$12$hgpOZw7bTi1Ky796XgJDMOsjh4oWeSRkaHVn71EgeO1xc4rA/TeGG', TRUE, 'STUDENT'),
       ('Nhat An', 'nhatan@example.com', '$2a$12$s0oTQbQkFQvW96GtQBsFbunQ7SkfqYikaw9LuLZp3Sq1QhYniVnJu', TRUE,
        'STUDENT'),
       ('Admin', 'admin@mysite.com', '$2a$12$F9xihGUaaA9eyKkdAOVwwOhUZkzEjQP0WzbR8HCUWraeE/7.fLIci', TRUE, 'ADMIN');

-- user_information
INSERT INTO user_information (account_id, full_name, gender, dob, phone, address, avatar_url)
VALUES (1, 'Alice Nguyen', FALSE, '2005-03-15', '0123456789', '123 Đường A, Quận 1, TP.HCM',
        'https://example.com/avatar/alice.jpg'),
       (2, 'Bob Tran', TRUE, '1985-10-20', '0987654321', '456 Đường B, Quận 3, TP.HCM', NULL),
       (3, 'Nguyen Hoang Nhat An', TRUE, '2004-08-20', '0901922117', '789 Đường C, Quận 5, TP.HCM',
        'https://example.com/avatar/nhatan.jpg');

-- subject
INSERT INTO subject (subject_name)
VALUES ('toán học'),
       ('ngữ văn'),
       ('ngoại ngữ'),
       ('vật lý'),
       ('hóa học'),
       ('sinh học'),
       ('lịch sử'),
       ('địa lý'),
       ('công nghệ'),
       ('tin học');


INSERT INTO block(code)
VALUES ('A00'),
       ('A01'),
       ('A02'),
       ('B00'),
       ('B03'),
       ('C00'),
       ('C01'),
       ('C02'),
       ('C03'),
       ('C04'),
       ('C05'),
       ('C06'),
       ('C07'),
       ('D01'),
       ('D07'),
       ('D08'),
       ('D10'),
       ('D14'),
       ('D15');

INSERT INTO block_subject(block_code, subject_id)
VALUES
    -- Khối A
    ('A00', 1), /* Toán */
    ('A00', 4), /* Vật lý */
    ('A00', 5), /* Hóa học */

    ('A01', 1), /* Toán */
    ('A01', 4), /* Vật lý */
    ('A01', 3), /* Tiếng Anh (ngoại ngữ) */

    ('A02', 1), /* Toán */
    ('A02', 4), /* Vật lý */
    ('A02', 6), /* Sinh học */

    -- Khối B
    ('B00', 1), /* Toán */
    ('B00', 5), /* Hóa học */
    ('B00', 6), /* Sinh học */

    ('B03', 1), /* Toán */
    ('B03', 6), /* Sinh học */
    ('B03', 2), /* Ngữ văn */

    -- Khối C
    ('C00', 2), /* Ngữ văn */
    ('C00', 7), /* Lịch sử */
    ('C00', 8), /* Địa lý */

    ('C01', 2), /* Ngữ văn */
    ('C01', 1), /* Toán */
    ('C01', 4), /* Vật lý */

    ('C02', 2), /* Ngữ văn */
    ('C02', 1), /* Toán */
    ('C02', 5), /* Hóa học */

    ('C03', 2), /* Ngữ văn */
    ('C03', 1), /* Toán */
    ('C03', 7), /* Lịch sử */

    ('C04', 2), /* Ngữ văn */
    ('C04', 1), /* Toán */
    ('C04', 8), /* Địa lý */

    ('C05', 2), /* Ngữ văn */
    ('C05', 4), /* Vật lý */
    ('C05', 5), /* Hóa học */

    ('C06', 2), /* Ngữ văn */
    ('C06', 4), /* Vật lý */
    ('C06', 6), /* Sinh học */

    ('C07', 2), /* Ngữ văn */
    ('C07', 4), /* Vật lý */
    ('C07', 7), /* Lịch sử */

    -- Khối D
    ('D01', 2), /* Ngữ văn */
    ('D01', 1), /* Toán */
    ('D01', 3), /* Tiếng Anh */

    ('D07', 1), /* Toán */
    ('D07', 5), /* Hóa học */
    ('D07', 3), /* Tiếng Anh */

    ('D08', 1), /* Toán */
    ('D08', 6), /* Sinh học */
    ('D08', 3), /* Tiếng Anh */

    ('D10', 1), /* Toán */
    ('D10', 8), /* Địa lý */
    ('D10', 3), /* Tiếng Anh */

    ('D14', 2), /* Ngữ văn */
    ('D14', 7), /* Lịch sử */
    ('D14', 3), /* Tiếng Anh */

    ('D15', 2), /* Ngữ văn */
    ('D15', 8), /* Địa lý */
    ('D15', 3);/* Tiếng Anh */


-- score
INSERT INTO score (subject_id, account_id, score_year_10, score_year_11, score_year_12)
VALUES (1, 1, 8.5, 8.0, 8.2),
       (3, 1, 9.0, 8.7, 8.9),
       (4, 1, 7.5, 8.1, 8.4),
       (2, 2, 8.8, 8.5, 8.6),
       (5, 2, 7.9, 8.2, 8.3);

-- "order"
INSERT INTO "order" (account_id, package_name, package_duration, price, order_date, expired_date, status, note)
VALUES
  (1, 'Premium', 30, 50000, '2025-05-01 10:30:00+07', '2025-08-01 10:30:00+07', 'ACTIVE',   'Đã thanh toán, đơn hàng đã được kích hoạt'),
  (2, 'Premium', 30, 50000, '2025-06-01 14:00:00+07', '2025-07-01 14:00:00+07', 'PENDING',  'Chờ thanh toán');

-- "transaction"
INSERT INTO "transaction" (order_id, amount, transaction_method, transaction_date, status, response_message)
VALUES
  (1, 50000, 'VNPay', '2025-05-01 10:31:00+07', 'SUCCESS', 'Thanh toán thành công'),
  (2, 50000, 'VNPay', '2025-06-01 14:05:00+07', 'FAILED',  'Thẻ không đủ tiền');
