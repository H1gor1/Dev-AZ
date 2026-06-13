INSERT INTO empresa ("razaoSocial", cnpj, logradouro, municipio, numero, complemento, bairro, cep, telefone, email, site, usuario, password, "createdAt", "updatedAt") VALUES
('Alpha Comércio Ltda', '11222333000181', 'Rua das Acácias', 'São Paulo', '100', 'Sala 1', 'Centro', '01001000', '11999990001', 'contato@alpha.com.br', 'www.alpha.com.br', 'alpha', 'senha123', NOW(), NOW()),
('Beta Distribuidora S.A.', '22333444000192', 'Av. Brasil', 'Rio de Janeiro', '500', NULL, 'Copacabana', '22040001', '21988880002', 'beta@betadist.com.br', 'www.betadist.com.br', 'beta', 'senha123', NOW(), NOW()),
('Gamma Indústria ME', '33444555000103', 'Rodovia dos Bandeirantes', 'Campinas', 'Km 15', 'Galpão 2', 'Distrito Industrial', '13054000', '19977770003', 'gamma@gamma.ind.br', 'www.gamma.ind.br', 'gamma', 'senha123', NOW(), NOW()),
('Delta Serviços Eireli', '44555666000114', 'Rua XV de Novembro', 'Curitiba', '250', 'Sala 302', 'Centro', '80020000', '41966660004', 'delta@delta.srv.br', 'www.delta.srv.br', 'delta', 'senha123', NOW(), NOW()),
('Epsilon Agro Ltda', '55666777000125', 'Estrada Municipal', 'Ribeirão Preto', 'S/N', 'Fazenda Boa Vista', 'Zona Rural', '14010000', '16955550005', 'epsilon@epsilonagro.com.br', 'www.epsilonagro.com.br', 'epsilon', 'senha123', NOW(), NOW()),
('Zeta Tecnologia S.A.', '66777888000136', 'Rua da Inovação', 'Florianópolis', '1000', 'Andar 5', 'Saco Grande', '88032001', '48944440006', 'zeta@zeta.tech', 'www.zeta.tech', 'zeta', 'senha123', NOW(), NOW()),
('Eta Transportes ME', '77888999000147', 'Av. das Nações', 'Uberlândia', '700', NULL, 'Santa Mônica', '38408280', '34933330007', 'eta@etatransportes.com.br', 'www.etatransportes.com.br', 'eta', 'senha123', NOW(), NOW()),
('Theta Alimentos S.A.', '88999000000158', 'Rua do Comércio', 'Goiânia', '320', 'Bloco A', 'Setor Sul', '74080001', '62922220008', 'theta@thetaalimentos.com.br', 'www.thetaalimentos.com.br', 'theta', 'senha123', NOW(), NOW()),
('Iota Construções Ltda', '99000111000169', 'Av. Paulista', 'São Paulo', '1500', 'Conjunto 80', 'Bela Vista', '01310001', '11911110009', 'iota@iotaconstrutora.com.br', 'www.iotaconstrutora.com.br', 'iota', 'senha123', NOW(), NOW()),
('Kappa Medicina Ltda', '10111222000170', 'Rua dos Médicos', 'Belo Horizonte', '50', NULL, 'Savassi', '30140001', '31900000010', 'kappa@kappamed.com.br', 'www.kappamed.com.br', 'kappa', 'senha123', NOW(), NOW());

INSERT INTO unidade (nome, "createdAt", "updatedAt") VALUES
('Quilograma', NOW(), NOW()),
('Metro', NOW(), NOW()),
('Litro', NOW(), NOW()),
('Unidade', NOW(), NOW()),
('Caixa', NOW(), NOW()),
('Tonelada', NOW(), NOW()),
('Metro Quadrado', NOW(), NOW()),
('Metro Cúbico', NOW(), NOW()),
('Par', NOW(), NOW()),
('Centímetro', NOW(), NOW());

INSERT INTO leilao (codigo, descricao, vendedor, "inicioPrevisto", "createdAt", "updatedAt") VALUES
(1001, 'Leilão de Máquinas Agrícolas', 1, '2026-07-15 09:00:00', NOW(), NOW()),
(1002, 'Leilão de Equipamentos Industriais', 2, '2026-07-20 10:00:00', NOW(), NOW()),
(1003, 'Leilão de Veículos Leves', 3, '2026-08-01 14:00:00', NOW(), NOW()),
(1004, 'Leilão de Sucata Metálica', 4, '2026-08-10 09:30:00', NOW(), NOW()),
(1005, 'Leilão de Produtos Alimentícios', 5, '2026-08-15 08:00:00', NOW(), NOW()),
(1006, 'Leilão de Equipamentos de TI', 6, '2026-09-01 11:00:00', NOW(), NOW()),
(1007, 'Leilão de Móveis e Utensílios', 7, '2026-09-10 13:00:00', NOW(), NOW()),
(1008, 'Leilão de Insumos Agrícolas', 5, '2026-09-20 09:00:00', NOW(), NOW()),
(1009, 'Leilão de Ferramentas', 8, '2026-10-01 10:00:00', NOW(), NOW()),
(1010, 'Leilão de Materiais de Construção', 9, '2026-10-15 08:30:00', NOW(), NOW());

INSERT INTO lote ("numeroLote", descricao, quantidade, "valorInicial", unidade_id, leilao_id, "createdAt", "updatedAt") VALUES
(1, 'Trator agrícola modelo XT', 1, 150000.00, 4, 1, NOW(), NOW()),
(2, 'Esteira transportadora industrial', 3, 25000.00, 4, 2, NOW(), NOW()),
(1, 'Frota de 5 veículos seminovos', 5, 200000.00, 4, 3, NOW(), NOW()),
(1, 'Sucata de aço carbono', 5000, 2.50, 6, 4, NOW(), NOW()),
(2, 'Sucata de cobre', 2000, 15.00, 6, 4, NOW(), NOW()),
(1, 'Arroz beneficiado tipo 1', 10000, 2.80, 1, 5, NOW(), NOW()),
(2, 'Feijão carioca', 8000, 4.50, 1, 5, NOW(), NOW()),
(1, 'Servidores blade Dell PowerEdge', 10, 35000.00, 4, 6, NOW(), NOW()),
(1, 'Lote de cadeiras executivas', 50, 120.00, 4, 7, NOW(), NOW()),
(1, 'Fertilizante NPK 20-20-20', 15000, 3.20, 1, 8, NOW(), NOW());

INSERT INTO comprador (empresa_id, leilao_id) VALUES
(2, 1),
(3, 1),
(4, 2),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 7),
(10, 8),
(1, 9);
