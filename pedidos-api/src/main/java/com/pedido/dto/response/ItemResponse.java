package com.pedido.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private Long id;

    private Long produtoId;

    private Long pedidoId;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private Integer quantidade;
}

