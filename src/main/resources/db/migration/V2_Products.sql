CREATE TABLE product
(
    id   SERIAL PRIMARY KEY,
    barcode VARCHAR(64) NOT NULL,
    name VARCHAR(64) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    food_processing VARCHAR(32),
    nurti_score VARCHAR(32),
    image_path VARCHAR(256)
);

CREATE TABLE nutriment
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE allergen
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE brand
(
    id    SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE product_allergen
(
    product_id BIGINT REFERENCES product (id),
    allergen_id BIGINT REFERENCES allergen (id),
    PRIMARY KEY (product_id, allergen_id)
);

CREATE TABLE product_brand
(
    product_id BIGINT REFERENCES product (id),
    brand_id BIGINT REFERENCES category (id),
    PRIMARY KEY (product_id, brand_id)
);

CREATE TABLE product_category
(
    product_id BIGINT REFERENCES product (id),
    category_id BIGINT REFERENCES category (id),
    PRIMARY KEY (product_id, category_id)
);

CREATE TABLE product_nutriment
(
    product_id BIGINT REFERENCES product (id),
    nutriment_id BIGINT REFERENCES nutriment (id),
    PRIMARY KEY (product_id, nutriment_id)
);

