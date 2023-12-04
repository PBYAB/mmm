CREATE TABLE product_image
(
    product_id BIGINT REFERENCES product (id),
    file_id    BIGINT REFERENCES file (id),

    PRIMARY KEY (product_id, file_id)
);

CREATE TABLE recipe_image
(
    recipe_id BIGINT REFERENCES recipe (id),
    file_id   BIGINT REFERENCES file (id),

    PRIMARY KEY (recipe_id, file_id)
);