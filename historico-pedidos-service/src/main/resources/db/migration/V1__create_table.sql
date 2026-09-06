create table historico(
    id int primary key not null,
    produto_id int not null,
    usuario_id int not null,
    valor_total decimal not null,
    quantidade int not null,
    data_compra date not null
);