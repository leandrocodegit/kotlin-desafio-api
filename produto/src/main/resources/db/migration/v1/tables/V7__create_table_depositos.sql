CREATE TABLE depositos(
id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
saldo INT,
local_id BIGINT NOT NULL,
produto_codigo VARCHAR(255) NULL
)