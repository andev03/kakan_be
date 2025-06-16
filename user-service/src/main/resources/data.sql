-- Dữ liệu mẫu

-- account
--password alice: password: hashed_pwd_1
--password bob: password: hashed_pwd_2
--password nhatan: password: hashed_pwd_3
--password admin: password: hashed_pwd_4

INSERT INTO account (email, password, full_name, is_active, role)
VALUES
  ('alice@example.com', '$2a$12$FsEQT3Zw0PeNv7iW5jmsF.6BZ70WKzIkdpqThESAnE.G2FhgvVoy6', 'Alice Nguyen', TRUE, 'STUDENT'),
  ('bob@example.com',   '$2a$12$hgpOZw7bTi1Ky796XgJDMOsjh4oWeSRkaHVn71EgeO1xc4rA/TeGG', 'Bob Tran',    TRUE, 'STUDENT'),
  ('nhatan@example.com', '$2a$12$s0oTQbQkFQvW96GtQBsFbunQ7SkfqYikaw9LuLZp3Sq1QhYniVnJu', 'Nguyen Hoang Nhat An', TRUE, 'STUDENT'),
  ('admin@mysite.com',  '$2a$12$F9xihGUaaA9eyKkdAOVwwOhUZkzEjQP0WzbR8HCUWraeE/7.fLIci', 'Admin User',  TRUE, 'ADMIN');

-- user_information
INSERT INTO user_information (account_id, gender, dob, phone, address, avatar_url)
VALUES
  (1, FALSE, '2005-03-15', '0123456789', '123 Đường A, Quận 1, TP.HCM', 'https://example.com/avatar/alice.jpg'),
  (2, TRUE,  '1985-10-20', '0987654321', '456 Đường B, Quận 3, TP.HCM', NULL),
  (3, TRUE, '2004-08-20', '0901922117', '789 Đường C, Quận 5, TP.HCM', 'https://example.com/avatar/nhatan.jpg');

-- subject
INSERT INTO subject (subject_name)
VALUES
  ('toán học'),
  ('ngữ văn'),
  ('ngoại ngữ'),
  ('vật lý'),
  ('hóa học'),
  ('sinh học'),
  ('lịch sử'),
  ('địa lý'),
  ('công nghệ'),
  ('tin học');

-- score
INSERT INTO score (subject_id, account_id, score_year_10, score_year_11, score_year_12)
VALUES
  (1, 1, 8.5, 8.0, 8.2),
  (3, 1, 9.0, 8.7, 8.9),
  (4, 1, 7.5, 8.1, 8.4),
  (2, 2, 8.8, 8.5, 8.6),
  (5, 2, 7.9, 8.2, 8.3);

-- "order"
INSERT INTO "order" (account_id, package_name, package_duration, price, order_date, expired_date, status, note)
VALUES
  (1, 'premium 3 months', 3, 1500000, '2025-05-01 10:30:00+07', '2025-08-01 10:30:00+07', 'ACTIVE',   'Thanh toán đầy đủ bằng thẻ'),
  (2, 'basic 1 month',   1, 300000,  '2025-06-01 14:00:00+07', '2025-07-01 14:00:00+07', 'PENDING',  'Chờ thanh toán');

-- "transaction"
INSERT INTO "transaction" (order_id, amount, transaction_method, transaction_date, status, response_message)
VALUES
  (1, 1500000, 'momo',        '2025-05-01 10:31:00+07', 'SUCCESS', 'Thanh toán thành công'),
  (2, 300000,  'credit_card', '2025-06-01 14:05:00+07', 'FAILED',  'Thẻ không đủ tiền');
