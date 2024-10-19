-- V2__create_ability_table.sql
CREATE TABLE Ability
(
    uuid varchar(50) PRIMARY KEY,
    name varchar(50) UNIQUE,
    url varchar(255) NOT NULL
);
