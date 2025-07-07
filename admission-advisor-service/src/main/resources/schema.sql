DROP TABLE IF EXISTS chat_message CASCADE;
DROP TABLE IF EXISTS chat_session CASCADE;

CREATE TABLE chat_message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sender_type VARCHAR(10) NOT NULL CHECK (sender_type IN ('user', 'bot')),
    sender_id INTEGER,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT now(),
    deleted_at TIMESTAMP
);