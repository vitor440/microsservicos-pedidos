CREATE TABLE `produto_reserva` (
           `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
           `produto_id` INT(10) NOT NULL,
           `quantidade` INT,
           FOREIGN KEY (produto_id) REFERENCES produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table produto add column quantidade_reserva INT NOT NULL;