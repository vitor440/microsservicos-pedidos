package com.pedido.kafka.consumer.dto;

import com.pedido.dto.request.ItemRequest;
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
    private List<ItemDTO> itens;
}
