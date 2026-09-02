package com.pedido.dto.response;

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
public class PedidoResponse {

    private Long id;

    private String usuarioId;

    private BigDecimal valorTotal;

    private String status;

    private LocalDate dataCompra;
}

