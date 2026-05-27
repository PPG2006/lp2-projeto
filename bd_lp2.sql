/* 
   CRIA TABELAS DO SISTEMA 
*/


CREATE TABLE professor (
    codigo_funcional INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE
);

CREATE TABLE curso (
    codigo INTEGER PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(500)
);

CREATE TABLE disciplina (
    numero INTEGER PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_inicio DATE,
    data_encerramento DATE,

    professor_codigo INTEGER,
    curso_codigo INTEGER,

    CONSTRAINT fk_disciplina_professor
        FOREIGN KEY (professor_codigo)
        REFERENCES professor(codigo_funcional),

    CONSTRAINT fk_disciplina_curso
        FOREIGN KEY (curso_codigo)
        REFERENCES curso(codigo)
);
