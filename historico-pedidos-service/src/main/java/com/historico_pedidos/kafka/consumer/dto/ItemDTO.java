package com.historico_pedidos.kafka.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long produtoId;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private Integer quantidade;
}
