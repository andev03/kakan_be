-- Drop ENUM types nếu tồn tại (để tránh lỗi khi chạy lại)
DROP TYPE IF EXISTS transaction_status CASCADE;
DROP TYPE IF EXISTS order_status CASCADE;


-- Drop bảng nếu tồn tại (có CASCADE để xóa các ràng buộc)
DROP TABLE IF EXISTS "transaction" CASCADE;
DROP TABLE IF EXISTS "order" CASCADE;
DROP TABLE IF EXISTS score CASCADE;
DROP TABLE IF EXISTS subject CASCADE;
DROP TABLE IF EXISTS block CASCADE;
DROP TABLE IF EXISTS block_subject CASCADE;
DROP TABLE IF EXISTS user_information CASCADE;
DROP TABLE IF EXISTS account CASCADE;




CREATE TYPE order_status AS ENUM (
  'PENDING',
  'ACTIVE',
  'EXPIRED',
  'CANCELLED'
);

CREATE TYPE transaction_status AS ENUM (
  'PENDING',
  'SUCCESS',
  'FAILED'
);

-- Tạo bảng account
CREATE TABLE account (
  id          SERIAL           PRIMARY KEY,
  user_name   VARCHAR(255)      NOT NULL UNIQUE,
  email       VARCHAR(255)     NOT NULL UNIQUE,
  password    VARCHAR(255)     NOT NULL,
  is_active   BOOLEAN          NOT NULL DEFAULT TRUE,
  role        VARCHAR(50)     NOT NULL,
  create_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Tạo bảng user_information
CREATE TABLE user_information (
  user_id     SERIAL           PRIMARY KEY,
  account_id  INTEGER          NOT NULL REFERENCES account(id) ON DELETE CASCADE,
  full_name   VARCHAR(50)      NOT NULL,
  gender      BOOLEAN        ,
  dob         DATE          ,
  phone       VARCHAR(20),
  address     TEXT,
  gpa           NUMERIC(4,2),
  avatar_url  TEXT
);

-- Tạo bảng subject
CREATE TABLE subject (
  subject_id    SERIAL         PRIMARY KEY,
  subject_name  VARCHAR(100)   NOT NULL UNIQUE
);
CREATE TABLE block (
    code VARCHAR(4) PRIMARY KEY
);

CREATE TABLE block_subject (
    block_code VARCHAR(4),
    subject_id INT,
    PRIMARY KEY(block_code, subject_id),
    FOREIGN KEY (block_code) REFERENCES block(code),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);


-- Tạo bảng score
CREATE TABLE score (
  score_id      SERIAL           PRIMARY KEY,
  subject_id    INTEGER          NOT NULL REFERENCES subject(subject_id) ON DELETE RESTRICT,
  account_id    INTEGER          NOT NULL REFERENCES account(id)      ON DELETE CASCADE,
  score_year_10 NUMERIC(4,2),
  score_year_11 NUMERIC(4,2),
  score_year_12 NUMERIC(4,2),
  CONSTRAINT uq_score_account_subject UNIQUE (account_id, subject_id)
);

-- Tạo bảng "order"
CREATE TABLE "order" (
  order_id         SERIAL                   PRIMARY KEY,
  account_id       INTEGER                  NOT NULL REFERENCES account(id) ON DELETE CASCADE,
  package_name     VARCHAR(255)             NOT NULL,
  package_duration INTEGER                  NOT NULL,
  price            NUMERIC(12,2)            NOT NULL,
  order_date       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  expired_date     TIMESTAMP WITH TIME ZONE NOT NULL,
  status           VARCHAR(50),
  note             TEXT
);

-- Tạo bảng "transaction"
CREATE TABLE "transaction" (
  transaction_id       SERIAL                   PRIMARY KEY,
  order_id             INTEGER                  NOT NULL REFERENCES "order"(order_id) ON DELETE CASCADE,
  amount               NUMERIC(12,2)            NOT NULL,
  transaction_method   VARCHAR(100)             NOT NULL,
  transaction_date     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  status               VARCHAR(50)       NOT NULL,
  response_message     TEXT
);
