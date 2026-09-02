CREATE TABLE `estatistica_produto` (
               id INT(10) AUTO_INCREMENT PRIMARY KEY,
               produto_id INT(10) NOT NULL,
               valor_total decimal(65,2) NOT NULL,
               quantidade INT(10) not null,
               data_compra date not null,
               FOREIGN KEY (produto_id) REFERENCES produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;