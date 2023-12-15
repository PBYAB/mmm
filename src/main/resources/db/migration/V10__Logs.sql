CREATE TABLE request_logs (
    id SERIAL PRIMARY KEY,
    uzer VARCHAR(255),
    endpoint VARCHAR(255),
    request_data TEXT,
    response_data TEXT,
    timestamp TIMESTAMP,
    ip TEXT
);