package com.historico_pedidos.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoResponse {

    private Long id;

    private Long produtoId;

    private String usuarioId;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;

    private Integer quantidade;

    private LocalDate dataCompra;
}
