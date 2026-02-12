CREATE DATABASE Colegio;

USE Colegio;

CREATE TABLE Instrumentos (
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(45),
    afinacao varchar(2)
);

CREATE TABLE Professores (
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(45),
    cpf varchar(14),
    dataNascimento date,
    email varchar(45),
    telefone varchar(17),
    dataEfetivacao date,
    especialidade varchar(20),
    ativo boolean    
);

CREATE TABLE Turmas (
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(60),
    sigla varchar(10), 
    turno varchar(5),
    nivel int, -- 1 iniciante, 2 intermediário, 3 avançado, 4 profissional.
    ativo boolean,
    teorico boolean, -- se verdadeiro, campo instrumento = null
    professor int,
    FOREIGN KEY (professor) REFERENCES Professores(id),
    instrumento int,
    FOREIGN KEY (instrumento) REFERENCES Instrumentos(id)
);

CREATE TABLE Alunos (
    idMatricula int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(45),
    cpf varchar(14),
    dataNascimento date,
    email varchar(45),
    telefone varchar(17),
    dataInscricao date,
    nomeResponsavel varchar(45),
    telefoneResponsavel varchar(17),
    instrumentoPratica int,
    FOREIGN KEY (instrumentoPratica) REFERENCES Instrumentos(id),
    instrumentoEmprestado int,
    -- A chave estrangeira para InstrumentosEscola será adicionada após a criação da tabela InstrumentosEscola
    ativo boolean,
    turma int,
    FOREIGN KEY (turma) REFERENCES Turmas(id)
) AUTO_INCREMENT = 10000;

CREATE TABLE InstrumentosEscola (
    id int AUTO_INCREMENT PRIMARY KEY,
    instrumento int,
    FOREIGN KEY (instrumento) REFERENCES Instrumentos(id),
    marca varchar(15),
    dataCompra date,
    alunoEmprestado int,
    dataEmprestimo date,
    FOREIGN KEY (alunoEmprestado) REFERENCES Alunos(idMatricula),
    disponivel boolean
);

ALTER TABLE Alunos ADD FOREIGN KEY (instrumentoEmprestado) REFERENCES InstrumentosEscola(id);

CREATE TABLE Partituras (
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(20),
    tomMaior varchar(2), -- Ex: "C#" (Dó sustenido maior), "Db" (Ré bemol maior), "E" (Mi maior)
    instrumento int,
    FOREIGN KEY (instrumento) REFERENCES Instrumentos(id),
    genero varchar(15),
    ano int,
    compositor varchar(45),
    formulaCompasso varchar(5) -- Ex: 4/4, 12/8, 12/16
);

-- Adicionando professores à tabela Professores

-- Professor 1
INSERT INTO Professores (nome, cpf, dataNascimento, email, telefone, dataEfetivacao, especialidade, ativo)
VALUES ('João Silva', '123.456.789-10', '1980-05-15', 'joao.silva@example.com', '(11) 98765-4321', '2020-01-10', 'Saxofone', TRUE);

-- Professor 2
INSERT INTO Professores (nome, cpf, dataNascimento, email, telefone, dataEfetivacao, especialidade, ativo)
VALUES ('Maria Santos', '987.654.321-00', '1975-08-20', 'maria.santos@example.com', '(11) 12345-6789', '2018-03-25', 'Clarinete', TRUE);

-- Professor 3
INSERT INTO Professores (nome, cpf, dataNascimento, email, telefone, dataEfetivacao, especialidade, ativo)
VALUES ('Pedro Oliveira', '456.789.123-00', '1988-11-30', 'pedro.oliveira@example.com', '(11) 23456-7890', '2019-07-15', 'Violino', TRUE);

-- Professor 4
INSERT INTO Professores (nome, cpf, dataNascimento, email, telefone, dataEfetivacao, especialidade, ativo)
VALUES ('Ana Costa', '789.123.456-00', '1983-04-05', 'ana.costa@example.com', '(11) 34567-8901', '2022-02-20', 'Trombone', TRUE);

-- Professor 5
INSERT INTO Professores (nome, cpf, dataNascimento, email, telefone, dataEfetivacao, especialidade, ativo)
VALUES ('Carlos Oliveira', '321.654.987-00', '1990-09-10', 'carlos.oliveira@example.com', '(11) 45678-9012', '2023-05-05', 'Violoncelo', TRUE);


INSERT INTO instrumentos (nome, afinacao) VALUES ('Saxofone Alto', 'Eb');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Saxofone Tenor', 'Bb');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Clarinete', 'Bb');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Flauta', 'C');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Trombone', 'C');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Trompete', 'Bb');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Tuba', 'C');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Trompa', 'F');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Violino', 'C');
INSERT INTO instrumentos (nome, afinacao) VALUES ('Violoncelo', 'C');

-- Inserção de instrumentos na tabela InstrumentosEscola

-- Saxofone Alto
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Saxofone Alto'), 'Yamaha', '2023-01-15', NULL, NULL, TRUE);

-- Saxofone Tenor
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Saxofone Tenor'), 'Selmer', '2023-02-20', NULL, NULL, TRUE);

-- Clarinete
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Clarinete'), 'Buffet', '2022-11-05', NULL, NULL, TRUE);

-- Flauta
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Flauta'), 'Yamaha', '2023-03-12', NULL, NULL, TRUE);

-- Trombone
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Trombone'), 'Bach', '2022-10-25', NULL, NULL, TRUE);

-- Trompete
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Trompete'), 'Bach', '2023-04-10', NULL, NULL, TRUE);

-- Tuba
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Tuba'), 'Yamaha', '2022-09-18', NULL, NULL, TRUE);

-- Trompa
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Trompa'), 'Conn', '2023-05-05', NULL, NULL, TRUE);

-- Violino
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Violino'), 'Stradivarius', '2022-08-21', NULL, NULL, TRUE);

-- Violoncelo
INSERT INTO InstrumentosEscola (instrumento, marca, dataCompra, alunoEmprestado, dataEmprestimo, disponivel)
VALUES ((SELECT id FROM Instrumentos WHERE nome = 'Violoncelo'), 'Stradivarius', '2022-07-30', NULL, NULL, TRUE);

-- inclusão de turmas padrões

-- turma Teorica
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Teoria Iniciante', 'TEORC-M124', 'Manhã', 1, TRUE, FALSE, 1, null);

-- Saxofone Alto para Iniciantes
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Saxofone Alto Iniciante', 'SAXOF-M124', 'Manhã', 1, TRUE, FALSE, 1, (SELECT id FROM Instrumentos WHERE nome = 'Saxofone Alto'));

-- Saxofone Tenor para Intermediários 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Saxofone Tenor Intermediário', 'SAXTN-T224', 'Tarde', 2, TRUE, FALSE, 2, (SELECT id FROM Instrumentos WHERE nome = 'Saxofone Tenor'));

-- Clarinete para Avançados 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Clarinete Avançado', 'CLARN-N324', 'Noite', 3, TRUE, FALSE, 3, (SELECT id FROM Instrumentos WHERE nome = 'Clarinete'));

-- Flauta para Profissionais 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Flauta Profissional', 'FLAUT-N424', 'Noite', 4, TRUE, FALSE, 4, (SELECT id FROM Instrumentos WHERE nome = 'Flauta'));

-- Trombone para Iniciantes
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Trombone Iniciante', 'TROMB-M124', 'Manhã', 1, TRUE, FALSE, 1, (SELECT id FROM Instrumentos WHERE nome = 'Trombone'));

-- Trompete para Intermediários 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Trompete Intermediário', 'TRMPT-T224', 'Tarde', 2, TRUE, FALSE, 2, (SELECT id FROM Instrumentos WHERE nome = 'Trompete'));

-- Tuba para Avançados 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Tuba Avançado', 'TUBA-N324', 'Noite', 3, TRUE, FALSE, 3, (SELECT id FROM Instrumentos WHERE nome = 'Tuba'));

-- Trompa para Profissionais 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Trompa Profissional', 'TRMPA-N424', 'Noite', 4, TRUE, FALSE, 4, (SELECT id FROM Instrumentos WHERE nome = 'Trompa'));

-- Violino para Iniciantes
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Violino Iniciante', 'VIOLN-M124', 'Manhã', 1, TRUE, FALSE, 1, (SELECT id FROM Instrumentos WHERE nome = 'Violino'));

-- Violoncelo para Avançados 
INSERT INTO Turmas (nome, sigla, turno, nivel, ativo, teorico, professor, instrumento)
VALUES ('Violoncelo Avançado', 'CELLO-N324', 'Noite', 3, TRUE, FALSE, 3, (SELECT id FROM Instrumentos WHERE nome = 'Violoncelo'));


