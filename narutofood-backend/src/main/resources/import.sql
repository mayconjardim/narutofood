insert into cozinha (nome) values ('Japonesa');
insert into cozinha (nome) values ('Francesa');

insert into restaurante (nome, taxa_frete, cozinha_id) values  ('Shoyu & Wasab', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values  ('Amour Bistrô', 10, 2);
insert into restaurante (nome, taxa_frete, cozinha_id) values  ('Petit Rio', 9.50, 2);

insert into estado (nome) values ('Rio de Janeiro');
insert into estado (nome) values ('São Paulo');
insert into estado (nome) values ('Belo Horizonte');

insert into cidade (nome, estado_id) values ('Rio de Janeiro', 1);
insert into cidade (nome, estado_id) values ('Niteroi', 1);
insert into cidade (nome, estado_id) values ('Guarulhos', 2);
insert into cidade (nome, estado_id) values ('Minas Gerais', 3);


