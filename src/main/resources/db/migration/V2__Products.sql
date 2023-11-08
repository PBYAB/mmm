CREATE TABLE product
(
    id   SERIAL PRIMARY KEY,
    barcode TEXT NOT NULL,
    name TEXT UNIQUE NOT NULL,
    quantity TEXT NOT NULL,
    food_processing TEXT
);

CREATE TABLE product_image
(
    id SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    size TEXT,
    content TEXT
);

CREATE TABLE nutriment
(
    product_id BIGINT REFERENCES product (id),
    energy_kcal_per_100g NUMERIC,
    fat_per_100g NUMERIC,
    fiber_per_100g NUMERIC,
    proteins_per_100g NUMERIC,
    salt_per_100g NUMERIC,
    sugars_per_100g NUMERIC,
    sodium_per_100g NUMERIC

);

CREATE TABLE product_ingredient_analysis
(
    product_id BIGINT REFERENCES product (id),
    vegan BOOLEAN,
    vegetarian BOOLEAN,
    from_palm_oil BOOLEAN
);

CREATE TABLE allergen
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE brand
(
    id    SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE product_allergen
(
    id SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    allergen_id BIGINT REFERENCES allergen (id)
);

CREATE TABLE product_brand
(
    id SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    brand_id BIGINT REFERENCES category (id)
);

CREATE TABLE product_category
(
    id SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    category_id BIGINT REFERENCES category (id)
);
