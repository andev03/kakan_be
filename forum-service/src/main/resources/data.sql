-- Insert 2 post có ID cố định
INSERT INTO post (id, user_id, content, like_count, comment_count)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'Hello world! Đây là bài đăng đầu tiên.', 5, 2),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 'Chào mọi người, hôm nay trời đẹp.', 3, 1);

-- Insert comment liên kết đúng post_id ở trên
INSERT INTO comment (id, post_id, user_id, content)
VALUES
  (gen_random_uuid(), 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222', 'Bình luận đầu tiên cho post 1'),
  (gen_random_uuid(), 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'Bình luận thứ hai cho post 1'),
  (gen_random_uuid(), 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 'Bình luận cho post 2');
