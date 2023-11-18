CREATE TABLE nutriment
(
    id                   SERIAL PRIMARY KEY,
    energy_kcal_per_100g NUMERIC,
    fat_per_100g         NUMERIC,
    fiber_per_100g       NUMERIC,
    proteins_per_100g    NUMERIC,
    salt_per_100g        NUMERIC,
    sugars_per_100g      NUMERIC,
    sodium_per_100g      NUMERIC
);

CREATE TABLE product_ingredient_analysis
(
    id                      SERIAL PRIMARY KEY,
    ingredients_description TEXT,
    vegan                   BOOLEAN,
    vegetarian              BOOLEAN,
    from_palm_oil           BOOLEAN
);

CREATE TABLE product
(
    id                     SERIAL PRIMARY KEY,
    barcode                TEXT        NOT NULL,
    name                   TEXT,
    quantity               TEXT,
    nutri_score            INT,
    nova_group             INT,
    ingredient_analysis_id BIGINT REFERENCES product_ingredient_analysis (id),
    nutriment_id           BIGINT REFERENCES nutriment (id)
);

CREATE TABLE product_image
(
    id         SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    size       TEXT,
    content    TEXT
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
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE product_allergen
(
    id          SERIAL PRIMARY KEY,
    product_id  BIGINT REFERENCES product (id),
    allergen_id BIGINT REFERENCES allergen (id)
);

CREATE TABLE product_brand
(
    id         SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    brand_id   BIGINT REFERENCES category (id)
);

CREATE TABLE product_category
(
    id          SERIAL PRIMARY KEY,
    product_id  BIGINT REFERENCES product (id),
    category_id BIGINT REFERENCES category (id)
);
