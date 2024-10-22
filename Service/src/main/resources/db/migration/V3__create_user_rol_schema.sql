-- V3__create_user_role_schema.sql

CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    role_id VARCHAR(50),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO users (username, password) VALUES ('Jesus', '123'), ('Mike', '123');
INSERT INTO roles (id, name) VALUES ('ADMIN', 'admin'), ('GUEST', 'guest');
INSERT INTO user_roles (username, role_id) VALUES ('Jesus', 'ADMIN'), ('Mike', 'GUEST');