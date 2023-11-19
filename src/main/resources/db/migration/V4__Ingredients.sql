CREATE TABLE product_ingredient
(
    id            SERIAL PRIMARY KEY,
    name          TEXT UNIQUE NOT NULL,
    quantity      INT,
    vegan         BOOLEAN,
    vegetarian    BOOLEAN,
    from_palm_oil BOOLEAN
);

CREATE TABLE product_ingredient_product
(
    id            SERIAL PRIMARY KEY,
    ingredient_id BIGINT REFERENCES product_ingredient (id),
    product_id    BIGINT REFERENCES product (id)
);

CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE product_country
(
    id         SERIAL PRIMARY KEY,
    country_id BIGINT REFERENCES country (id),
    product_id BIGINT REFERENCES product (id)
);