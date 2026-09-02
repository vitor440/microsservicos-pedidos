CREATE TABLE `produto` (
        `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
        `nome` longtext,
        `preco` decimal(65,2) NOT NULL,
        `quantidade` INT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;