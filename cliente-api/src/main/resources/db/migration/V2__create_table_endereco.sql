CREATE TABLE endereco (
id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
rua VARCHAR(40) NOT NULL,
numero VARCHAR(8) NULL,
cidade VARCHAR(40) NULL,
estado VARCHAR(4) NULL,
cep VARCHAR(10) NOT NULL);

CREATE TABLE usuario_enderecos (
usuario_ID BIGINT NOT NULL,
enderecos_id BIGINT NULL);