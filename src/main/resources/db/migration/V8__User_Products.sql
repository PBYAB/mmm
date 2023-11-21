CREATE TABLE user_product
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES app_user (id),
    product_id BIGINT REFERENCES product (id),
    quantity   INT NOT NULL
);