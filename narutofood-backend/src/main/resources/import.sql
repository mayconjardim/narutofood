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

insert into forma_pagamento(descricao) values ('Pix');
insert into forma_pagamento(descricao) values ('Cartão de Crédito');
insert into forma_pagamento(descricao) values ('Cartão de Debito');
insert into forma_pagamento(descricao) values ('Dinheiro');

insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (1, 1);
insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (1, 2);
insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (1, 3);
insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (1, 4);

insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (2, 2);
insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (2, 3);
insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (2, 4);

insert into restaurantes_forma_pagamento(restaurante_id, forma_pagamento_id	) values (3, 4);





