package com.produtos.repository;

import com.produtos.model.EstatisticaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstatisticaProdutoRepository extends JpaRepository<EstatisticaProduto, Long> {
}
