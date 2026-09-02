package com.produtos.dto.response;

import com.produtos.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaProdutoResponse {

    private Long id;

    private Long produtoId;

    private String nome;

    private Integer quantidade;

    private BigDecimal valorTotal;

    private LocalDate dataCompra;

}
