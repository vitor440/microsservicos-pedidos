package com.pedido.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

    private Long id;

    private String nome;

    private BigDecimal preco;

    private Integer quantidade;

    private Integer quantidadeReserva;


}
