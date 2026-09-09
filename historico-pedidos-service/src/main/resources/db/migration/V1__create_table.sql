create table historico(
    id int primary key auto_increment,
    produto_id int not null,
    usuario_id varchar(500) not null,
    valor_unitario decimal not null,
    valor_total decimal not null,
    quantidade int not null,
    data_compra date not null
);
