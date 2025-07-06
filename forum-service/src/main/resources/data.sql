INSERT INTO topic (name)
VALUES
    ('Introduction'),
    ('Programming'),
    ('Technology'),
    ('Others');

INSERT INTO post (id, account_id, title, content, like_count, comment_count, status)
VALUES
    ('00000000-0000-0000-0000-000000000001', 1, 'Welcome Post', 'Hello world from user 1!', 1, 2,'ACTIVE'),
    ('00000000-0000-0000-0000-000000000002', 2, 'Spring Boot Tips', 'Spring Boot tips and tricks', 1, 1, 'ACTIVE'),
    ('00000000-0000-0000-0000-000000000003', 3, 'Kotlin Dilemma', 'Should I learn Kotlin?', 1, 0, 'BLOCKED');

INSERT INTO post_topic (post_id, topic_id)
VALUES
    ('00000000-0000-0000-0000-000000000001', 1),
    ('00000000-0000-0000-0000-000000000002', 2),
    ('00000000-0000-0000-0000-000000000003', 3);

INSERT INTO comment (post_id, account_id, content)
VALUES
    ('00000000-0000-0000-0000-000000000001', 2, 'Nice post!'),
    ('00000000-0000-0000-0000-000000000001', 3, 'Agree with you.'),
    ('00000000-0000-0000-0000-000000000002', 1, 'Thanks for sharing!');

INSERT INTO post_like (post_id, account_id)
VALUES
    ('00000000-0000-0000-0000-000000000001', 3),
    ('00000000-0000-0000-0000-000000000002', 1),
    ('00000000-0000-0000-0000-000000000003', 2);

INSERT INTO report (post_id, reporter_id, reason)
VALUES
    ('00000000-0000-0000-0000-000000000003', 1, 'Spam content'),
    ('00000000-0000-0000-0000-000000000003', 2, 'Offensive language');
