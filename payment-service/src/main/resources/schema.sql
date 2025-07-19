DROP TABLE IF EXISTS payment CASCADE;

CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    payment_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    status VARCHAR(10) NOT NULL,
    response_message TEXT,
    payment_url TEXT,

    CONSTRAINT uq_order_id UNIQUE (order_id)
);
