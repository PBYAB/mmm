CREATE TABLE file
(
    id           SERIAL PRIMARY KEY,
    name         TEXT        NOT NULL,
    path         TEXT        NOT NULL,
    size         BIGINT      NOT NULL,
    content_type TEXT,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT current_timestamp
);