DROP TABLE IF EXISTS chat_message CASCADE;
DROP TABLE IF EXISTS chat_session CASCADE;

CREATE TABLE chat_session (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    started_at TIMESTAMP DEFAULT now(),
    status VARCHAR(20) DEFAULT 'active'
);

CREATE TABLE chat_message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    session_id UUID NOT NULL REFERENCES chat_session(id) ON DELETE CASCADE,
    sender_type VARCHAR(10) NOT NULL CHECK (sender_type IN ('user', 'bot')),
    sender_id UUID,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT now(),
    deleted_at TIMESTAMP
);