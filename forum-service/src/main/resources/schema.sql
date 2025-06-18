DROP TABLE IF EXISTS post_like CASCADE;
DROP TABLE IF EXISTS report CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS post CASCADE;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE post (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    like_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    status TEXT DEFAULT 'ACTIVE'
);

CREATE TABLE comment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID NOT NULL REFERENCES post(id) ON DELETE CASCADE,
    account_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE post_like (
    post_id UUID NOT NULL REFERENCES post(id) ON DELETE CASCADE,
    account_id INTEGER NOT NULL,
    liked_at TIMESTAMP DEFAULT now(),
    PRIMARY KEY (post_id, account_id)
);

CREATE TABLE report (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID NOT NULL REFERENCES post(id) ON DELETE CASCADE,
    reporter_id INTEGER NOT NULL,
    reason TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);
