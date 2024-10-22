CREATE TABLE roles (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role_id VARCHAR(50),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO roles (id, name) VALUES ('ADMIN', 'admin'), ('GUEST', 'guest');

INSERT INTO users (username, password, role_id) VALUES 
    ('Jesus', '123', 'ADMIN'), 
    ('Mike', '123', 'GUEST');