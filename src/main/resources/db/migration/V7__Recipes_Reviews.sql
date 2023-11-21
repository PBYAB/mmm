CREATE TABLE recipe_review
(
    id        SERIAL PRIMARY KEY,
    user_id   BIGINT REFERENCES app_user (id),
    recipe_id BIGINT REFERENCES recipe (id),
    rating    NUMERIC(3, 2) NOT NULL,
    comment   TEXT,

    UNIQUE (user_id, recipe_id)
);

