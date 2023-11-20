CREATE TABLE user_favourite_recipe
(
    user_id BIGINT REFERENCES app_user (id),
    recipe_id BIGINT REFERENCES recipe (id),
    PRIMARY KEY (user_id, recipe_id)
);