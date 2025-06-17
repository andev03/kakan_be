INSERT INTO chat_session (id, account_id, title) VALUES
    (
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        1,
        'User 1111 Chat with Bot'
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        1,
        'User 2222 Chat with Bot'
    );

INSERT INTO chat_message (session_id, sender_type, sender_id, content)
VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'user',
    1,
    'Hello, how are you?'
);

INSERT INTO chat_message (session_id, sender_type, content)
VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'bot',
    'Hi there! I am a chatbot. How can I assist you today?'
);