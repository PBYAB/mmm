CREATE TABLE Users
(
    user_id  SERIAL PRIMARY KEY,
    name     VARCHAR(20)         NOT NULL,
    surname  VARCHAR(50)         NOT NULL,
    email    VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    enabled  BOOLEAN             NOT NULL
);

CREATE TABLE Roles
(
    role_id SERIAL PRIMARY KEY,
    role    VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE UsersRoles
(
    user_id BIGINT REFERENCES Users (user_id),
    role_id BIGINT REFERENCES Roles (role_id),

    PRIMARY KEY (user_id, role_id)
);