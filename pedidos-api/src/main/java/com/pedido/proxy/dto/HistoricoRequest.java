package com.pedido.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRequest {


    private Long produtoId;

    private Long usuarioId;

    private BigDecimal valorTotal;

    private Integer quantidade;

}
