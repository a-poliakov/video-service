# create schema if not exists ru.apolyakov.social_network;

create table if not exists users
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    first_name  VARCHAR(100) NOT NULL,
    second_name VARCHAR(100) NOT NULL,
    city        VARCHAR(100)
);

create index first_second_name_idx on users(first_name, second_name);

create index first_name_idx on users(first_name);
create index second_name_idx on users(second_name);