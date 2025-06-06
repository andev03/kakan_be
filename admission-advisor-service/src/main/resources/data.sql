INSERT INTO chat_session (id, user_id) VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111'
);

-- Insert tin nhắn người dùng gửi bot
INSERT INTO chat_message (session_id, sender_type, sender_id, content)
VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'user',
    '11111111-1111-1111-1111-111111111111',
    'Hello, how are you?'
);

-- Insert tin nhắn bot trả lời
INSERT INTO chat_message (session_id, sender_type, content)
VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'bot',
    'Hi there! I am a chatbot. How can I assist you today?'
);