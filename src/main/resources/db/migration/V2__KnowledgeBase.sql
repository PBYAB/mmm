CREATE TABLE article_categories
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE articles
(
    id          SERIAL PRIMARY KEY,
    category_id BIGINT      NOT NULL REFERENCES article_categories (id),
    title       TEXT        NOT NULL,
    content     TEXT        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL,
    status      TEXT        NOT NULL
)