CREATE TABLE recipe
(
    id                      SERIAL PRIMARY KEY,
    name                    TEXT NOT NULL,
    instructions            TEXT,
    servings                INT,
    energy_kcal_per_serving NUMERIC
);

CREATE TABLE ingredient
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE recipe_ingredient
(
    id            SERIAL PRIMARY KEY,
    recipe_id     BIGINT REFERENCES recipe (id)     NOT NULL,
    ingredient_id BIGINT REFERENCES ingredient (id) NOT NULL,
    quantity      NUMERIC,
    unit          TEXT
);


