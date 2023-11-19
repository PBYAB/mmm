CREATE TABLE app_user
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(20)         NOT NULL,
    last_name  VARCHAR(50)         NOT NULL,
    email      VARCHAR(128) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    enabled    BOOLEAN             NOT NULL
);

CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);

INSERT INTO role (name)
VALUES ('ADMIN'),
       ('USER'),
       ('LIMITED_USER');

CREATE TABLE user_role
(
    user_id BIGINT REFERENCES app_user (id),
    role_id BIGINT REFERENCES role (id),

    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE auth_token
(
    id         SERIAL PRIMARY KEY,
    token      VARCHAR(4096) UNIQUE            NOT NULL,
    token_type VARCHAR(255)                    NOT NULL,
    revoked    BOOLEAN                         NOT NULL,
    expired    BOOLEAN                         NOT NULL,
    user_id    BIGINT REFERENCES app_user (id) NOT NULL
);