-- V1__create_pokemon_table.sql
CREATE TABLE Pokemon
(
    uuid varchar(50) PRIMARY KEY,
    id varchar(50) UNIQUE,
    name varchar(100) NOT NULL
);
