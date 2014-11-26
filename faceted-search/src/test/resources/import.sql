INSERT INTO Company (id, active, name) VALUES (1, 1, 'MUTT CO');
INSERT INTO Company (id, active, name) VALUES (2, 1, 'DELICIAS');
INSERT INTO Company (id, active, name) VALUES (3, 1, 'ESTRELA');
INSERT INTO Company (id, active, name) VALUES (4, 1, 'SOARES');

INSERT INTO State (id, name, acronym) VALUES (1, 'SAO PAULO', 'SP');
INSERT INTO State (id, name, acronym) VALUES (2, 'MINAS GERAIS', 'MG');

INSERT INTO Company_State (companies_id, states_id) VALUES (1, 1);
INSERT INTO Company_State (companies_id, states_id) VALUES (2, 1);
INSERT INTO Company_State (companies_id, states_id) VALUES (3, 2);
INSERT INTO Company_State (companies_id, states_id) VALUES (4, 2);