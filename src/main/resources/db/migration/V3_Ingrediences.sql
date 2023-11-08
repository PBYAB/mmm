CREATE TABLE ingredient
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    vegan BOOLEAN NOT NULL,
    vegetarian BOOLEAN NOT NULL
);

CREATE TABLE product_ingredient
(
    ingredinet_id   BIGINT REFERENCES ingredient (id),
    product_id BIGINT REFERENCES product (id)
);
