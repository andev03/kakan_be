INSERT INTO topic (name)
VALUES
    ('Introduction'),
    ('Programming'),
    ('Technology');

INSERT INTO post (id, account_id, title, topic_id, content, status)
VALUES
    ('00000000-0000-0000-0000-000000000001', 1, 'Welcome Post', 1, 'Hello world from user 1!', 'ACTIVE'),
    ('00000000-0000-0000-0000-000000000002', 2, 'Spring Boot Tips', 2, 'Spring Boot tips and tricks', 'ACTIVE'),
    ('00000000-0000-0000-0000-000000000003', 3, 'Kotlin Dilemma', 3, 'Should I learn Kotlin?', 'BLOCKED');

INSERT INTO comment (post_id, account_id, content)
VALUES
    ('00000000-0000-0000-0000-000000000001', 2, 'Nice post!'),
    ('00000000-0000-0000-0000-000000000001', 3, 'Agree with you.'),
    ('00000000-0000-0000-0000-000000000002', 1, 'Thanks for sharing!');

INSERT INTO post_like (post_id, account_id)
VALUES
    ('00000000-0000-0000-0000-000000000001', 2),
    ('00000000-0000-0000-0000-000000000001', 3),
    ('00000000-0000-0000-0000-000000000002', 1);

INSERT INTO report (post_id, reporter_id, reason)
VALUES
    ('00000000-0000-0000-0000-000000000003', 1, 'Spam content'),
    ('00000000-0000-0000-0000-000000000003', 2, 'Offensive language');
