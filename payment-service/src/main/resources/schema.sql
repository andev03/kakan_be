-- schema.sql
-- Drop bảng nếu đã tồn tại để đảm bảo môi trường sạch
DROP TABLE IF EXISTS payment CASCADE;

-- Tạo bảng payment dựa trên POJO của bạn
CREATE TABLE payment (
                         payment_id SERIAL PRIMARY KEY, -- SERIAL tự động tăng cho Integer ID (PostgreSQL)
                         order_id INTEGER NOT NULL,
                         account_id INTEGER NOT NULL, -- Thêm cột account_id từ POJO
                         amount DOUBLE PRECISION NOT NULL, -- DOUBLE PRECISION cho Double trong Java
                         payment_method VARCHAR(100) NOT NULL,
                         payment_date TIMESTAMP WITH TIME ZONE NOT NULL, -- TIMESTAMP WITH TIME ZONE cho OffsetDateTime
                         status VARCHAR(10) NOT NULL,
                         response_message TEXT,

    -- Thêm ràng buộc UNIQUE cho order_id nếu mỗi order chỉ có một payment duy nhất
                         CONSTRAINT uq_order_id UNIQUE (order_id)
);

-- Bạn có thể thêm các index nếu cần để tối ưu truy vấn
-- CREATE INDEX idx_payment_order_id ON payment (order_id);
-- CREATE INDEX idx_payment_status ON payment (status);