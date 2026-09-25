CREATE TABLE `item` (
           `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
           produto_id INT(10) NOT NULL,
           pedido_id INT(10) NOT NULL,
           preco_unitario decimal(65,2) NOT NULL,
           `preco_total` decimal(65,2) NOT NULL,
           `quantidade` INT,
           FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;