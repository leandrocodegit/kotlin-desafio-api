CREATE TABLE IF NOT EXISTS TB_LIVRO(
    id_book int primary key auto_increment,
    ds_name varchar(100) not null,
    ds_author varchar(200) not null,
    publish_year int not null
);