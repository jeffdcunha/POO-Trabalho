create database PetshopDB;

use PetshopDB;

CREATE TABLE pets ( 
    id INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100),
    especie VARCHAR(100),
    raca VARCHAR(100),
    idade int,
    peso float,
    PRIMARY KEY(id)
);

insert into pets (nome, especie, raca, idade, peso)
values ('Creuzebeck', 'gato', 'siames', 3, 4.5);

insert into pets (nome, especie, raca, idade, peso)
values ('Edileuza', 'cachorro', 'caramelo', 5, 15);

CREATE TABLE racoes ( 
    id INT AUTO_INCREMENT NOT NULL,
    marca VARCHAR(100),
    sabor VARCHAR(100),
    variante VARCHAR(100),
    quantidade int,
    peso float,
    PRIMARY KEY(id)
);

insert into racoes (marca, sabor, variante, quantidade, peso)
values ('Whiskas', 'frango', 'castrado', 15, 1.5);

insert into racoes (marca, sabor, variante, quantidade, peso)
values ('Golden', 'carne', 'filhote', 10, 10);