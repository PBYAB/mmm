CREATE TABLE ingredient
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    quantity INT NOT NULL,
    vegan BOOLEAN,
    vegetarian BOOLEAN,
    from_palm_oil BOOLEAN
);

CREATE TABLE product_ingredient
(
    ingredient_id   BIGINT REFERENCES ingredient (id),
    product_id BIGINT REFERENCES product (id)
);
