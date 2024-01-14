CREATE TABLE user_recipe_of_the_day
(
    id        SERIAL PRIMARY KEY,
    user_id   BIGINT      NOT NULL REFERENCES app_user (id),
    recipe_id BIGINT      NOT NULL REFERENCES recipe (id),
    drawn_at  TIMESTAMPTZ NOT NULL
)