create database if not exists `product_management`;
use `product_management`;


create table if not exists `products` (
    `id`              int             not null				AUTO_INCREMENT,
    `name`            varchar (255)   not null,
    `description`     varchar (255)   not null,
    `price`           decimal(10, 2)   not null,
    `categoryPath`    varchar (255)   not null,
    `availability`    BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (id)
);


create table if not exists `user`(
    `id`              int             not null				AUTO_INCREMENT,
    `first_name`      varchar (255)   not null,
    `last_name`       varchar (255)   not null,
    `email`           varchar (255)   not null,
    `password`        varchar (255)   not null,
    PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
);
