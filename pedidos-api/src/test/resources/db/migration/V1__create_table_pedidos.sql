CREATE TABLE `pedidos` (
           `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
           usuario_id longtext NOT NULL,
           valor_total decimal(65,2) NOT NULL,
           `status` longtext,
           data_compra date not null
) ENGINE=InnoDB DEFAULT CHARSET=latin1;