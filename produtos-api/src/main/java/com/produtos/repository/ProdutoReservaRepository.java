package com.produtos.repository;

import com.produtos.model.ProdutoReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoReservaRepository extends JpaRepository<ProdutoReserva, Long> {
}
