package com.produtos.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {

    private Long pedidoId;
    private String usuarioId;
    private List<ItemRequest> itens;
}
