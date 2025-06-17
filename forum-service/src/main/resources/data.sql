INSERT INTO post (id, user_id, content, like_count, comment_count)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, 'Hello world! Đây là bài đăng đầu tiên.', 5, 1),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 1, 'Chào mọi người, hôm nay trời đẹp.', 3, 1);

INSERT INTO comment (post_id, user_id, content)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, 'Bình luận thứ hai cho post 1'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 1, 'Bình luận cho post 2');
