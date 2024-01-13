CREATE TABLE product_image
(
    id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product(id),
    url TEXT NOT NULL,
    size TEXT NOT NULL
)